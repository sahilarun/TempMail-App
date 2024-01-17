package com.sahil.tmailapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sahil.tmailapp.Adapter.EmailAdapter;
import com.sahil.tmailapp.Config;
import com.sahil.tmailapp.Model.EmailMessage;
import com.sahil.tmailapp.R;
import com.sahil.tmailapp.Utils.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InboxFragment extends Fragment {

    private View view;
    private PrefManager prefManager;
    private EmailAdapter emailAdapter;
    private SwipeRefreshLayout swipeToRefresh;
    private RelativeLayout noEmailLayout;
    private List<EmailMessage> emailMessages;

    public InboxFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inbox, container, false);

        prefManager = new PrefManager(getActivity());
        noEmailLayout = view.findViewById(R.id.noEmailLayout);
        swipeToRefresh = view.findViewById(R.id.swipeToRefresh);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        emailMessages = new ArrayList<>(); // Populate this list with your email data
        emailAdapter = new EmailAdapter(getActivity(), emailMessages);
        recyclerView.setAdapter(emailAdapter);

        emailAdapter.notifyDataSetChanged();

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simulateData();
            }
        });

        return view;
    }

    private void simulateData() {
        swipeToRefresh.setRefreshing(true);
        String url = Config.MAIN_URL+"/messages";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        swipeToRefresh.setRefreshing(false);
                        JSONArray messagesArray = response.getJSONArray("messages");
                        List<EmailMessage> emailMessages = new ArrayList<>();

                        Log.d("ymgss oks",emailMessages+"");
                        if (emailMessages.isEmpty()){
                            noEmailLayout.setVisibility(View.VISIBLE);
                        }

                        for (int i = 0; i < messagesArray.length(); i++) {
                            noEmailLayout.setVisibility(View.GONE);
                            JSONObject messageObject = messagesArray.getJSONObject(i);
                            EmailMessage emailMessage = new EmailMessage();
                            emailMessage.setId(messageObject.getString("_id"));
                            emailMessage.setReceivedAt(messageObject.getLong("receivedAt"));
                            emailMessage.setFrom(messageObject.getString("from"));
                            emailMessage.setSubject(messageObject.getString("subject"));
                            emailMessage.setBodyPreview(messageObject.getString("bodyPreview"));
                            emailMessage.setAttachmentsCount(messageObject.getInt("attachmentsCount"));

                            emailMessages.add(0,emailMessage);
                            Log.d("ymgss ok",messageObject+"");
                        }

                        emailAdapter.setEmailMessages(emailMessages);
                        emailAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle Volley error
                    swipeToRefresh.setRefreshing(false);
                    noEmailLayout.setVisibility(View.VISIBLE);
                    Log.d("ymgss Index",error+"");
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                //headers.put("Authorization", "Bearer "+prefManager.getString("TOKEN"));
                headers.put("Authorization", "Bearer "+prefManager.getString("TOKEN"));
                headers.put("X-RapidAPI-Key", Config.API_KEY);
                headers.put("X-RapidAPI-Host", Config.MAIN_URL.replace("https://",""));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onResume() {
        super.onResume();
        emailMessages.clear();
        emailAdapter.notifyDataSetChanged();
        simulateData();
    }
}