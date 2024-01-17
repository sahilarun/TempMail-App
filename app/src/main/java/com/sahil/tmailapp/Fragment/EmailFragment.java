package com.sahil.tmailapp.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sahil.tmailapp.Config;
import com.sahil.tmailapp.R;
import com.sahil.tmailapp.Utils.AdsManager;
import com.sahil.tmailapp.Utils.AdsPref;
import com.sahil.tmailapp.Utils.DatabaseHelper;
import com.sahil.tmailapp.Utils.PrefManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class EmailFragment extends Fragment {

    private View view;
    TextView emailTextView;
    private PrefManager prefManager;
    private LinearLayout btnEmailChange;
    private LinearLayout btnEmailCopy;
    private DatabaseHelper dbHelper;

    private AdsPref adsPref;
    private AdsManager adsManager;

    public EmailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_email, container, false);
        prefManager = new PrefManager(getContext());
        dbHelper = new DatabaseHelper(getContext());

        adsPref = new AdsPref(getContext());
        adsManager = new AdsManager(getActivity());

        adsManager.loadInterstitialAd(adsPref.getIsInterstitialPostList(), adsPref.getInterstitialAdInterval());

        emailTextView = view.findViewById(R.id.emailTextView);
        btnEmailChange = view.findViewById(R.id.btnEmailChange);
        btnEmailCopy = view.findViewById(R.id.btnEmailCopy);

        if (prefManager.getString("EMAIL").equals("")){
            getNewEmailAddress();
        }else {
            emailTextView.setText(prefManager.getString("EMAIL"));
        }

        btnEmailChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewEmailAddress();
                showInterstitialAd();
            }
        });

        btnEmailCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text", prefManager.getString("EMAIL"));
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(), "Email copied to clipboard", Toast.LENGTH_SHORT).show();
                showInterstitialAd();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (prefManager.getString("EMAIL").equals("")){
            getNewEmailAddress();
        }else {
            emailTextView.setText(prefManager.getString("EMAIL"));
        }
    }

    private void showInterstitialAd() {
        if (adsPref.getInterstitialAdCounter() >= adsPref.getInterstitialAdInterval()) {
            adsPref.updateInterstitialAdCounter(1);
            adsManager.showInterstitialAd();
        } else {
            adsPref.updateInterstitialAdCounter(adsPref.getInterstitialAdCounter() + 1);
        }
    }

    private void getNewEmailAddress() {
        String url = Config.MAIN_URL+"/mailbox";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the JSON response here
                        String jsonResponse = response.toString();

                        try {
                            // Extract values from the JSON response
                            String TOKEN = response.getString("token");
                            String EMAIL = response.getString("mailbox");

                            prefManager.setString("EMAIL",EMAIL);
                            prefManager.setString("TOKEN",TOKEN);
                            dbHelper.insertUserCredentials(EMAIL, TOKEN);
                            emailTextView.setText(EMAIL);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), e+"", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occurred during the request
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Key", Config.API_KEY);
                headers.put("X-RapidAPI-Host", Config.MAIN_URL.replace("https://",""));
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}