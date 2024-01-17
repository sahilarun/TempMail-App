package com.sahil.tmailapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AdsPref {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AdsPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("ads_settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAds(boolean adStatus, String adType, String backupAds, String adMobAppId, String adMobBannerId, String adMobInterstitialId, String adMobNativeId, String adMobAppOpenId, String appLovinBannerId, String appLovinInterstitialId, String applovinNativeAdManualUnitId, String applovinAppOpenAdUnitId, String applovinBannerZoneId, String applovinBannerMrecZoneId, String applovinInterstitialZoneId, int interstitialAdInterval, int nativeAdIndex, String nativeAdStylePostList, String nativeAdStylePostDetails, String nativeAdStyleExitDialog) {
        editor.putBoolean("ad_status", adStatus);
        editor.putString("main_ads", adType);
        editor.putString("backup_ads", backupAds);
        editor.putString("admob_app_id", adMobAppId);
        editor.putString("admob_banner_unit_id", adMobBannerId);
        editor.putString("admob_interstitial_unit_id", adMobInterstitialId);
        editor.putString("admob_native_unit_id", adMobNativeId);
        editor.putString("admob_app_open_ad_unit_id", adMobAppOpenId);
        editor.putString("applovin_banner_ad_unit_id", appLovinBannerId);
        editor.putString("applovin_interstitial_ad_unit_id", appLovinInterstitialId);
        editor.putString("applovin_native_ad_manual_unit_id", applovinNativeAdManualUnitId);
        editor.putString("applovin_app_open_ad_unit_id", applovinAppOpenAdUnitId);
        editor.putString("applovin_banner_zone_id", applovinBannerZoneId);
        editor.putString("applovin_banner_mrec_zone_id", applovinBannerMrecZoneId);
        editor.putString("applovin_interstitial_zone_id", applovinInterstitialZoneId);
        editor.putInt("interstitial_ad_interval", interstitialAdInterval);
        editor.putInt("native_ad_index", nativeAdIndex);
        editor.putString("native_ad_style_post_list", nativeAdStylePostList);
        editor.putString("native_ad_style_post_details", nativeAdStylePostDetails);
        editor.putString("native_ad_style_exit_dialog", nativeAdStyleExitDialog);
        editor.apply();
    }

    public boolean getAdStatus() {
        return sharedPreferences.getBoolean("ad_status", true);
    }

    public String getMainAds() {
        return sharedPreferences.getString("main_ads", "0");
    }

    public String getBackupAds() {
        return sharedPreferences.getString("backup_ads", "none");
    }

    public String getAdMobPublisherId() {
        return sharedPreferences.getString("admob_publisher_id", "0");
    }

    public String getAdMobBannerId() {
        return sharedPreferences.getString("admob_banner_unit_id", "0");
    }

    public String getAdMobInterstitialId() {
        return sharedPreferences.getString("admob_interstitial_unit_id", "0");
    }

    public String getAdMobNativeId() {
        return sharedPreferences.getString("admob_native_unit_id", "0");
    }

    public String getAdMobAppOpenAdId() {
        return sharedPreferences.getString("admob_app_open_ad_unit_id", "0");
    }

    public String getAdManagerBannerId() {
        return sharedPreferences.getString("ad_manager_banner_unit_id", "0");
    }

    public String getAdManagerInterstitialId() {
        return sharedPreferences.getString("ad_manager_interstitial_unit_id", "0");
    }

    public String getAdManagerNativeId() {
        return sharedPreferences.getString("ad_manager_native_unit_id", "0");
    }

    public String getAdManagerAppOpenAdId() {
        return sharedPreferences.getString("ad_manager_app_open_ad_unit_id", "0");
    }

    public String getFanBannerId() {
        return sharedPreferences.getString("fan_banner_unit_id", "0");
    }

    public String getFanInterstitialId() {
        return sharedPreferences.getString("fan_interstitial_unit_id", "0");
    }

    public String getFanNativeId() {
        return sharedPreferences.getString("fan_native_unit_id", "0");
    }

    public String getStartappAppId() {
        return sharedPreferences.getString("startapp_app_id", "0");
    }

    public String getUnityGameId() {
        return sharedPreferences.getString("unity_game_id", "0");
    }

    public String getUnityBannerPlacementId() {
        return sharedPreferences.getString("unity_banner_placement_id", "banner");
    }

    public String getUnityInterstitialPlacementId() {
        return sharedPreferences.getString("unity_interstitial_placement_id", "video");
    }

    public String getAppLovinBannerAdUnitId() {
        return sharedPreferences.getString("applovin_banner_ad_unit_id", "0");
    }

    public String getAppLovinInterstitialAdUnitId() {
        return sharedPreferences.getString("applovin_interstitial_ad_unit_id", "0");
    }

    public String getAppLovinNativeAdManualUnitId() {
        return sharedPreferences.getString("applovin_native_ad_manual_unit_id", "0");
    }

    public String getAppLovinAppOpenAdUnitId() {
        return sharedPreferences.getString("applovin_app_open_ad_unit_id", "0");
    }

    public String getAppLovinBannerZoneId() {
        return sharedPreferences.getString("applovin_banner_zone_id", "0");
    }

    public String getAppLovinBannerMrecZoneId() {
        return sharedPreferences.getString("applovin_banner_mrec_zone_id", "0");
    }

    public String getAppLovinInterstitialZoneId() {
        return sharedPreferences.getString("applovin_interstitial_zone_id", "0");
    }

    public String getIronSourceAppKey() {
        return sharedPreferences.getString("ironsource_app_key", "0");
    }

    public String getIronSourceBannerId() {
        return sharedPreferences.getString("ironsource_banner_id", "0");
    }

    public String getIronSourceInterstitialId() {
        return sharedPreferences.getString("ironsource_interstitial_id", "0");
    }

    public String getWortiseAppId() {
        return sharedPreferences.getString("wortise_app_id", "");
    }

    public String getWortiseBannerAdUnitId() {
        return sharedPreferences.getString("wortise_banner_ad_unit_id", "");
    }

    public String getWortiseInterstitialAdUnitId() {
        return sharedPreferences.getString("wortise_interstitial_ad_unit_id", "");
    }

    public String getWortiseNativeAdUnitId() {
        return sharedPreferences.getString("wortise_native_ad_unit_id", "");
    }

    public String getWortiseAppOpenAdUnitId() {
        return sharedPreferences.getString("wortise_app_open_ad_unit_id", "");
    }

    public int getInterstitialAdInterval() {
        return sharedPreferences.getInt("interstitial_ad_interval", 3);
    }

    public int getNativeAdIndex() {
        return sharedPreferences.getInt("native_ad_index", 3);
    }

    public String getNativeAdStylePostList() {
        return sharedPreferences.getString("native_ad_style_post_list", "medium");
    }

    public String getNativeAdStylePostDetails() {
        return sharedPreferences.getString("native_ad_style_post_details", "large");
    }

    public String getNativeAdStyleExitDialog() {
        return sharedPreferences.getString("native_ad_style_exit_dialog", "large");
    }

    public Integer getInterstitialAdCounter() {
        return sharedPreferences.getInt("interstitial_counter", 1);
    }

    public void updateInterstitialAdCounter(int counter) {
        editor.putInt("interstitial_counter", counter);
        editor.apply();
    }

    public void setAdPlacements(boolean bannerHome, boolean bannerPostDetails, boolean bannerCategoryDetails, boolean bannerSearch, boolean interstitialPostList, boolean interstitialPostDetails, boolean nativePostList, boolean nativePostDetails, boolean nativeExitDialog, boolean appOpenAdOnStart, boolean appOpenAdOnResume) {
        editor.putBoolean("banner_home", bannerHome);
        editor.putBoolean("banner_post_details", bannerPostDetails);
        editor.putBoolean("banner_category_details", bannerCategoryDetails);
        editor.putBoolean("banner_search", bannerSearch);
        editor.putBoolean("interstitial_post_list", interstitialPostList);
        editor.putBoolean("interstitial_post_details", interstitialPostDetails);
        editor.putBoolean("native_post_list", nativePostList);
        editor.putBoolean("native_post_details", nativePostDetails);
        editor.putBoolean("native_exit_dialog", nativeExitDialog);
        editor.putBoolean("app_open_ad_on_start", appOpenAdOnStart);
        editor.putBoolean("app_open_ad_on_resume", appOpenAdOnResume);
        editor.apply();
    }

    public boolean getIsBannerHome() {
        return sharedPreferences.getBoolean("banner_home", true);
    }

    public boolean getIsBannerPostDetails() {
        return sharedPreferences.getBoolean("banner_post_details", true);
    }

    public boolean getIsBannerCategoryDetails() {
        return sharedPreferences.getBoolean("banner_category_details", true);
    }

    public boolean getIsBannerSearch() {
        return sharedPreferences.getBoolean("banner_search", true);
    }

    public boolean getIsInterstitialPostList() {
        return sharedPreferences.getBoolean("interstitial_post_list", true);
    }

    public boolean getIsInterstitialPostDetails() {
        return sharedPreferences.getBoolean("interstitial_post_details", true);
    }

    public boolean getIsNativePostList() {
        return sharedPreferences.getBoolean("native_post_list", true);
    }

    public boolean getIsNativePostDetails() {
        return sharedPreferences.getBoolean("native_post_details", true);
    }

    public boolean getIsNativeExitDialog() {
        return sharedPreferences.getBoolean("native_exit_dialog", true);
    }

    public boolean getIsAppOpenAdOnStart() {
        return sharedPreferences.getBoolean("app_open_ad_on_start", true);
    }

    public boolean getIsAppOpenAdOnResume() {
        return sharedPreferences.getBoolean("app_open_ad_on_resume", true);
    }

}
