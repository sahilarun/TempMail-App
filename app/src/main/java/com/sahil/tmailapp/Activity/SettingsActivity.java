package com.sahil.tmailapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.sahil.tmailapp.Config;
import com.sahil.tmailapp.R;
import com.sahil.tmailapp.Utils.PrefManager;

import java.io.File;
import java.text.DecimalFormat;

public class SettingsActivity extends AppCompatActivity {

    TextView tvCurrentVersion;
    TextView tvSaveLocation;

    TextView tvNotificationTag;
    TextView tvColumns;
    LinearLayout linearLayoutPolicyPrivacy;
    LinearLayout linearLayoutMoreApp;


    Switch switchButtonNotification;
    AlertDialog alertDialog1;
    CharSequence[] values = {" 2 Columns "," 3 Columns "};
    PrefManager prefManager;
    Switch switchDarkMode;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("Settings");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager = new PrefManager(this);

        tvCurrentVersion = findViewById(R.id.tvCurrentVersion);

        tvNotificationTag = findViewById(R.id.tvNotificationTag);
        linearLayoutMoreApp = findViewById(R.id.linearLayoutMoreApp);

        linearLayoutPolicyPrivacy = findViewById(R.id.linearLayoutPolicyPrivacy);

        switchButtonNotification = findViewById(R.id.switchButtonNotification);
        switchDarkMode = findViewById(R.id.switch_button_animation);


        tvNotificationTag.setText(getResources().getString(R.string.label_notification)+getResources().getString(R.string.app_name));



        linearLayoutPolicyPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.privacy_policy_url)));
                startActivity(browserIntent);
            }
        });
        linearLayoutMoreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.instagram.com/"+ Config.INSTAGRAM));
                startActivity(browserIntent);
            }
        });

        //Night Mode
        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public final void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    prefManager.setNightModeState(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    onResume();
                }else {
                    prefManager.setNightModeState(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    onResume();
                }
            }
        });

    }

    public long getDirSize(File dir) {
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0 Bytes";
        }
        String[] units = new String[]{"Bytes", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10((double) size) / Math.log10(1024.0d));
        StringBuilder stringBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.#");
        double d = (double) size;
        double pow = Math.pow(1024.0d, (double) digitGroups);
        Double.isNaN(d);
        stringBuilder.append(decimalFormat.format(d / pow));
        stringBuilder.append(" ");
        stringBuilder.append(units[digitGroups]);
        return stringBuilder.toString();
    }

    private void clearCache() {

//        FileUtils.deleteQuietly(getCacheDir());
//        FileUtils.deleteQuietly(getExternalCacheDir());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("SetTextI18n")
    public void onResume() {
        super.onResume();

        if (prefManager.loadNightModeState()==true){
            switchDarkMode.setChecked(true);
        }else {
            switchDarkMode.setChecked(false);

        }
        initCheck();

    }

    private void initCheck() {
        if (prefManager.loadNightModeState()){
            Log.d("Dark", "MODE");
        }else {
             // set status text dark
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}