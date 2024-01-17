package com.sahil.tmailapp.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.android.volley.VolleyError;
import com.gdevs.mycipher.AndroidCipher;
import com.sahil.tmailapp.Config;
import com.sahil.tmailapp.R;
import com.sahil.tmailapp.Utils.AdsPref;
import com.sahil.tmailapp.Utils.Anims;
import com.sahil.tmailapp.Utils.MyJsonFetcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private TextView developers;
    private RelativeLayout parentLayout;
    private AppCompatImageView logo;
    private AdsPref adsPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.white)); // Set the color you want
        }

        developers = findViewById(R.id.developers);
        parentLayout = findViewById(R.id.parentLayout);


        logo = findViewById(R.id.logo);
        Anims aVar = new Anims(this.getResources().getDrawable(R.drawable.logo));
        aVar.m14932a(true);
        logo.setImageDrawable(aVar);
        logo.setVisibility(View.VISIBLE);

        adsPref = new AdsPref(this);
        adsPref.saveAds(
                Config.AD_STATUS,
                Config.AD_NETWORK,
                Config.BACKUP_AD_NETWORK,
                "",
                Config.ADMOB_BANNER_ID,
                Config.ADMOB_INTERSTITIAL_ID,
                Config.ADMOB_NATIVE_ID,
                Config.ADMOB_APP_OPEN_AD_ID,
                Config.APPLOVIN_BANNER_ID,
                Config.APPLOVIN_INTERSTITIAL_ID,
                Config.APPLOVIN_NATIVE_MANUAL_ID,
                Config.APPLOVIN_APP_OPEN_AP_ID,
                Config.APPLOVIN_BANNER_ZONE_ID,
                Config.APPLOVIN_BANNER_MREC_ZONE_ID,
                Config.APPLOVIN_INTERSTITIAL_ZONE_ID,
                Config.INTERSTITIAL_AD_INTERVAL,
                Config.NATIVE_AD_INDEX,
                Config.NATIVE_STYLE,
                Config.STYLE_NEWS,
                Config.STYLE_RADIO
        );


        MyJsonFetcher jsonFetcher = new MyJsonFetcher(this);

        jsonFetcher.fetchJsonData(AndroidCipher.getCipher(Config.API), new MyJsonFetcher.JsonCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    String U = response.getString("url");
                    String D = response.getString("dev");
                    Config.MAIN_URL = AndroidCipher.getCipher(U);
                    Config.DEV = AndroidCipher.getCipher(D);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                goToNextActivity();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });



    }

    private void goToNextActivity() {
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // If you want to modify a view in your Activity
                SplashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();

                    }
                });
            }
        }, 3000);
    }
}
