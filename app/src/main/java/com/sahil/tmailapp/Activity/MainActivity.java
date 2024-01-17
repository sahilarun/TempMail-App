package com.sahil.tmailapp.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sahil.tmailapp.Config;
import com.sahil.tmailapp.Fragment.EmailFragment;
import com.sahil.tmailapp.Fragment.HistoryFragment;
import com.sahil.tmailapp.Fragment.InboxFragment;
import com.sahil.tmailapp.R;
import com.sahil.tmailapp.Utils.AdsManager;
import com.sahil.tmailapp.Utils.AdsPref;
import com.sahil.tmailapp.Utils.PrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolBar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ViewPager viewPager;
    PrefManager prefManager;
    private static final String TAG = "MainActivity";
    private static final String ONESIGNAL_APP_ID = "f3b5e68d-ef4f-4a48-8f4b-b45a0170c3e5";
    private BottomNavigationView navigation;
    int pager_number = 3;
    private AdsPref adsPref;
    private AdsManager adsManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        prefManager = new PrefManager(this);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        adsPref = new AdsPref(this);
        adsManager = new AdsManager(this);
        adsManager.initializeAd();
        adsManager.updateConsentStatus();
        adsManager.loadBannerAd(adsPref.getIsBannerHome());
        adsManager.loadInterstitialAd(adsPref.getIsInterstitialPostList(), adsPref.getInterstitialAdInterval());

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        toolBar.setNavigationIcon(R.drawable.ic_action_action);

        initViewPager();


    }

    @SuppressWarnings("deprecation")
    public void initViewPager() {
        viewPager = findViewById(R.id.viewPager);
        navigation = findViewById(R.id.navigation);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(pager_number);
        navigation.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_email) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.navigation_inbox) {
                viewPager.setCurrentItem(1);
                return true;
            } else if (itemId == R.id.navigation_history) {
                viewPager.setCurrentItem(2);
                return true;
            }
            return false;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public class MyAdapter extends FragmentPagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new EmailFragment();
                case 1:
                    return new InboxFragment();
                case 2:
                    return new HistoryFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return pager_number;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "https://play.google.com/store/apps/details?id="+getPackageName();
            intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT,shareBodyText);
            startActivity(Intent.createChooser(intent,"share via"));
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);

        if (menuItem.getItemId() == R.id.nav_setting){
            showInterstitialAd();
            startActivity(new Intent(this, SettingsActivity.class));
        }
        if (menuItem.getItemId() == R.id.nav_about){
            showAbout();
        }
        if (menuItem.getItemId() == R.id.nav_insta){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.instagram.com/"+ Config.INSTAGRAM));
            startActivity(browserIntent);
        }
        if (menuItem.getItemId() == R.id.nav_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "https://play.google.com/store/apps/details?id="+getPackageName();
            intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT,shareBodyText);
            startActivity(Intent.createChooser(intent,"share via"));

        }
        if (menuItem.getItemId() == R.id.nav_rate){
            try {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
            }catch (ActivityNotFoundException ex){
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
            }
        }
        if (menuItem.getItemId() == R.id.nav_feedback){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{Config.CONTACT_EMAIL});
            intent.putExtra(Intent.EXTRA_SUBJECT, Config.CONTACT_SUBJECT);
            intent.putExtra(Intent.EXTRA_TEXT, Config.CONTACT_TEXT);
            try {
                startActivity(Intent.createChooser(intent, "Send mail"));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    private void showAbout() {
        final Dialog customDialog;
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_about, null);
        customDialog = new Dialog(this, R.style.DialogCustomTheme);
        customDialog.setContentView(customView);
        TextView tvClose = customDialog.findViewById(R.id.tvClose);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    public void onResume() {
        super.onResume();
        initCheck();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            showExitDialog();
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

    private void showExitDialog() {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_exit);

        LinearLayout mbtnYes = dialog.findViewById(R.id.mbtnYes);
        LinearLayout mbtnNo = dialog.findViewById(R.id.mbtnNo);
        mbtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        mbtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initCheck() {
        if (prefManager.loadNightModeState()==true){
            Log.d("Dark", "MODE");
        }else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);   // set status text dark
        }
    }
}