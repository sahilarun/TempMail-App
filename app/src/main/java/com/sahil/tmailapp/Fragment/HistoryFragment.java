package com.sahil.tmailapp.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sahil.tmailapp.Adapter.UserCredentialsAdapter;
import com.sahil.tmailapp.Config;
import com.sahil.tmailapp.R;
import com.sahil.tmailapp.Utils.DatabaseHelper;
import com.sahil.tmailapp.Utils.PrefManager;


public class HistoryFragment extends Fragment {

    private View view;
    TextView emailTextView;
    private PrefManager prefManager;
    private DatabaseHelper dbHelper;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_inbox, container, false);
        prefManager = new PrefManager(getContext());
        dbHelper = new DatabaseHelper(getContext());


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Config.DEV.equals(Config.DEVELOPERS_NAME)){
            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            Cursor cursor = dbHelper.getAllUserCredentials();
            UserCredentialsAdapter adapter = new UserCredentialsAdapter(getActivity(), cursor);
            recyclerView.setAdapter(adapter);
        }
    }
}