package com.sahil.tmailapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.sahil.tmailapp.Utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private TextView emailFromShort;
    private TextView emailFromName;
    private TextView emailFromEmail;
    private TextView emailDate;
    private TextView emailSubject;
    private WebView emailBody;
    private String emailId;
    private SwipeRefreshLayout swipeToRefresh;
    private LinearLayout detailsPage;
    private PrefManager prefManager;
    private AdsPref adsPref;
    private AdsManager adsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        emailId = getIntent().getStringExtra("emailId");
        prefManager = new PrefManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        adsPref = new AdsPref(this);
        adsManager = new AdsManager(this);
        adsManager.loadBannerAd(adsPref.getIsBannerHome());
        adsManager.loadInterstitialAd(adsPref.getIsInterstitialPostList(), adsPref.getInterstitialAdInterval());

        swipeToRefresh = findViewById(R.id.swipeToRefresh);
        detailsPage = findViewById(R.id.detailsPage);
        emailFromShort = findViewById(R.id.emailFromShort);
        emailFromName = findViewById(R.id.emailFromName);
        emailFromEmail = findViewById(R.id.emailFromEmail);
        emailDate = findViewById(R.id.emailDate);
        emailSubject = findViewById(R.id.emailSubject);
        emailBody = findViewById(R.id.emailBody);


        getEmailDetails();

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                detailsPage.setVisibility(View.GONE);
                getEmailDetails();            }
        });

        emailBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitialAd();
            }
        });

    }

    private void showInterstitialAd() {
        if (adsPref.getInterstitialAdCounter() >= adsPref.getInterstitialAdInterval()) {
            adsPref.updateInterstitialAdCounter(1);
            adsManager.showInterstitialAd();
        } else {
            adsPref.updateInterstitialAdCounter(adsPref.getInterstitialAdCounter() + 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Config.DEV.equals(Config.DEVELOPERS_NAME)){
            finish();
        }
    }

    private void getEmailDetails() {
        // Create a RequestQueue
        swipeToRefresh.setRefreshing(true);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Config.MAIN_URL + "/messages/" + emailId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        swipeToRefresh.setRefreshing(false);
                        detailsPage.setVisibility(View.VISIBLE);
                        try {
                            // Parse the JSON response
                            String receivedAt = response.getString("receivedAt");
                            String mailbox = response.getString("mailbox");
                            String from = response.getString("from");
                            String subject = response.getString("subject");
                            String bodyPreview = response.getString("bodyPreview");
                            String bodyHtml = response.getString("bodyHtml");
                            String createdAt = response.getString("createdAt");

                            String[] fromName = from.split("<");
                            String firstCharacter = String.valueOf(fromName[0].charAt(0));

                            emailFromShort.setText(firstCharacter);
                            emailFromName.setText(fromName[0]);
                            emailFromEmail.setText(fromName[1].replace(">", ""));
                            emailDate.setText(getFormattedReceivedAt(Long.parseLong(receivedAt)));
                            emailSubject.setText(subject);
                            getEmailBody(bodyHtml);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        swipeToRefresh.setRefreshing(false);
                        detailsPage.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                // Create a HashMap for headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+prefManager.getString("TOKEN"));
                headers.put("X-RapidAPI-Key", Config.API_KEY);
                headers.put("X-RapidAPI-Host", Config.MAIN_URL.replace("https://",""));
                return headers;
            }
        };

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getEmailBody(String bodyHtml) {
        String cssStyle = "<style type='text/css'>\n" +
                "@font-face {\n" +
                "    font-family: 'CustomFont';\n" +
                "    src: url('file:///android_asset/fonts/custom_font.ttf');\n" +
                "}\n" +
                "body {\n" +
                "    font-family: 'CustomFont', sans-serif;\n" +
                "    /* Add any other styling you need here */\n" +
                "}\n" +
                "</style>";

        String htmlContent = "<html><head>" + cssStyle + "</head><body>" + bodyHtml + "</body></html>";

        emailBody.getSettings().setJavaScriptEnabled(true);
        emailBody.getSettings().setDisplayZoomControls(false);
        emailBody.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }


    public String getFormattedReceivedAt(long receivedAt) {
        // Convert timestamp to human-readable date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date(receivedAt * 1000));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
