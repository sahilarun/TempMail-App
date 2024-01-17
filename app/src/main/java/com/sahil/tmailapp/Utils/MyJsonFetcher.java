package com.sahil.tmailapp.Utils;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class MyJsonFetcher {

    private RequestQueue requestQueue;

    public MyJsonFetcher(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchJsonData(String url, final JsonCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the JSON response here
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occurred during the request
                        callback.onError(error);
                    }
                }
        );

        // Add the request to the Volley request queue
        requestQueue.add(jsonObjectRequest);
    }

    public interface JsonCallback {
        void onSuccess(JSONObject response);
        void onError(VolleyError error);
    }
}

