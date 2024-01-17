package com.sahil.tmailapp.Utils;


import static com.ymg.ads.sdk.util.Constant.IRONSOURCE;

import android.app.Activity;
import android.view.View;


import com.sahil.tmailapp.Config;
import com.sahil.tmailapp.Model.Ads;
import com.sahil.tmailapp.R;
import com.ymg.ads.sdk.BuildConfig;
import com.ymg.ads.sdk.format.AdNetwork;
import com.ymg.ads.sdk.format.AppOpenAd;
import com.ymg.ads.sdk.format.BannerAd;
import com.ymg.ads.sdk.format.InterstitialAd;
import com.ymg.ads.sdk.format.NativeAd;
import com.ymg.ads.sdk.format.NativeAdView;
import com.ymg.ads.sdk.gdpr.GDPR;
import com.ymg.ads.sdk.gdpr.LegacyGDPR;
import com.ymg.ads.sdk.util.OnShowAdCompleteListener;


public class AdsManager {

    Activity activity;
    AdNetwork.Initialize adNetwork;

    AppOpenAd.Builder appOpenAd;
    BannerAd.Builder bannerAd;
    InterstitialAd.Builder interstitialAd;
    NativeAd.Builder nativeAd;
    NativeAdView.Builder nativeAdView;
    PrefManager sharedPref;
    AdsPref adsPref;
    LegacyGDPR legacyGDPR;
    GDPR gdpr;

    public AdsManager(Activity activity) {
        this.activity = activity;
        this.sharedPref = new PrefManager(activity);
        this.adsPref = new AdsPref(activity);
        this.legacyGDPR = new LegacyGDPR(activity);
        this.gdpr = new GDPR(activity);
        adNetwork = new AdNetwork.Initialize(activity);
        appOpenAd = new AppOpenAd.Builder(activity);
        bannerAd = new BannerAd.Builder(activity);
        interstitialAd = new InterstitialAd.Builder(activity);
        nativeAd = new NativeAd.Builder(activity);
        nativeAdView = new NativeAdView.Builder(activity);
    }

    public void initializeAd() {
        if (adsPref.getAdStatus()) {
            adNetwork.setAdStatus("1")
                    .setAdNetwork(adsPref.getMainAds())
                    .setBackupAdNetwork(adsPref.getBackupAds())
                    .setStartappAppId(adsPref.getStartappAppId())
                    .setUnityGameId(adsPref.getUnityGameId())
                    .setIronSourceAppKey(adsPref.getIronSourceAppKey())
                    .setWortiseAppId(adsPref.getWortiseAppId())
                    .setDebug(BuildConfig.DEBUG)
                    .build();
        }
    }

    public void loadAppOpenAd(boolean placement, OnShowAdCompleteListener onShowAdCompleteListener) {
        if (placement) {
            if (adsPref.getAdStatus()) {
                appOpenAd = new AppOpenAd.Builder(activity)
                        .setAdStatus("1")
                        .setAdNetwork(adsPref.getMainAds())
                        .setBackupAdNetwork(adsPref.getBackupAds())
                        .setAdMobAppOpenId(adsPref.getAdMobAppOpenAdId())
                        .setAdManagerAppOpenId(adsPref.getAdManagerAppOpenAdId())
                        .setApplovinAppOpenId(adsPref.getAppLovinAppOpenAdUnitId())
                        .setWortiseAppOpenId(adsPref.getWortiseAppOpenAdUnitId())
                        .build(onShowAdCompleteListener);
            } else {
                onShowAdCompleteListener.onShowAdComplete();
            }
        } else {
            onShowAdCompleteListener.onShowAdComplete();
        }
    }

    public void loadAppOpenAd(boolean placement) {
        if (placement) {
            if (adsPref.getAdStatus()) {
                appOpenAd = new AppOpenAd.Builder(activity)
                        .setAdStatus("1")
                        .setAdNetwork(adsPref.getMainAds())
                        .setBackupAdNetwork(adsPref.getBackupAds())
                        .setAdMobAppOpenId(adsPref.getAdMobAppOpenAdId())
                        .setAdManagerAppOpenId(adsPref.getAdManagerAppOpenAdId())
                        .setApplovinAppOpenId(adsPref.getAppLovinAppOpenAdUnitId())
                        .setWortiseAppOpenId(adsPref.getWortiseAppOpenAdUnitId())
                        .build();
            }
        }
    }

    public void showAppOpenAd(boolean placement) {
        if (placement) {
            appOpenAd.show();
        }
    }

    public void destroyAppOpenAd(boolean placement) {
        if (placement) {
            appOpenAd.destroyOpenAd();
        }
    }

    public void loadBannerAd(boolean placement) {
        if (placement) {
            if (adsPref.getAdStatus()) {
                bannerAd.setAdStatus("1")
                        .setAdNetwork(adsPref.getMainAds())
                        .setBackupAdNetwork(adsPref.getBackupAds())
                        .setAdMobBannerId(adsPref.getAdMobBannerId())
                        .setGoogleAdManagerBannerId(adsPref.getAdManagerBannerId())
                        .setFanBannerId(adsPref.getFanBannerId())
                        .setUnityBannerId(adsPref.getUnityBannerPlacementId())
                        .setAppLovinBannerId(adsPref.getAppLovinBannerAdUnitId())
                        .setAppLovinBannerZoneId(adsPref.getAppLovinBannerZoneId())
                        .setIronSourceBannerId(adsPref.getIronSourceBannerId())
                        .setWortiseBannerId(adsPref.getWortiseBannerAdUnitId())
                        .setDarkTheme(sharedPref.loadNightModeState())
                        .setPlacementStatus(1)
                        .setLegacyGDPR(Config.LEGACY_GDPR)
                        .build();
            }
        }
    }

    public void loadInterstitialAd(boolean placement, int interval) {
        if (placement) {
            if (adsPref.getAdStatus()) {
                interstitialAd.setAdStatus("1")
                        .setAdNetwork(adsPref.getMainAds())
                        .setBackupAdNetwork(adsPref.getBackupAds())
                        .setAdMobInterstitialId(adsPref.getAdMobInterstitialId())
                        .setGoogleAdManagerInterstitialId(adsPref.getAdManagerInterstitialId())
                        .setFanInterstitialId(adsPref.getFanInterstitialId())
                        .setUnityInterstitialId(adsPref.getUnityInterstitialPlacementId())
                        .setAppLovinInterstitialId(adsPref.getAppLovinInterstitialAdUnitId())
                        .setAppLovinInterstitialZoneId(adsPref.getAppLovinInterstitialZoneId())
                        .setIronSourceInterstitialId(adsPref.getIronSourceInterstitialId())
                        .setWortiseInterstitialId(adsPref.getWortiseInterstitialAdUnitId())
                        .setInterval(interval)
                        .setPlacementStatus(1)
                        .setLegacyGDPR(Config.LEGACY_GDPR)
                        .build();
            }
        }
    }

    public void loadNativeAd(boolean placement, String style) {
        if (placement) {
            if (adsPref.getAdStatus()) {
                nativeAd.setAdStatus("1")
                        .setAdNetwork(adsPref.getMainAds())
                        .setBackupAdNetwork(adsPref.getBackupAds())
                        .setAdMobNativeId(adsPref.getAdMobNativeId())
                        .setAdManagerNativeId(adsPref.getAdManagerNativeId())
                        .setFanNativeId(adsPref.getFanNativeId())
                        .setAppLovinNativeId(adsPref.getAppLovinNativeAdManualUnitId())
                        .setWortiseNativeId(adsPref.getWortiseNativeAdUnitId())
                        .setPlacementStatus(1)
                        .setDarkTheme(sharedPref.loadNightModeState())
                        .setLegacyGDPR(Config.LEGACY_GDPR)
                        .setNativeAdStyle(style)
                        .setNativeAdBackgroundColor(R.color.white, R.color.black)
                        .build();
            }
        }
    }

    public void loadNativeAdView(View view, boolean placement, String style) {
        if (placement) {
            if (adsPref.getAdStatus()) {
                nativeAdView.setAdStatus("1")
                        .setAdNetwork(adsPref.getMainAds())
                        .setBackupAdNetwork(adsPref.getBackupAds())
                        .setAdMobNativeId(adsPref.getAdMobNativeId())
                        .setAdManagerNativeId(adsPref.getAdManagerNativeId())
                        .setFanNativeId(adsPref.getFanNativeId())
                        .setAppLovinNativeId(adsPref.getAppLovinNativeAdManualUnitId())
                        .setWortiseNativeId(adsPref.getWortiseNativeAdUnitId())
                        .setPlacementStatus(1)
                        .setDarkTheme(sharedPref.loadNightModeState())
                        .setLegacyGDPR(Config.LEGACY_GDPR)
                        .setNativeAdStyle(style)
                        .setView(view)
                        .setNativeAdBackgroundColor(R.color.white, R.color.black)
                        .build();
            }
        }
    }

    public void showInterstitialAd() {
        interstitialAd.show();
    }

    public void destroyBannerAd() {
        bannerAd.destroyAndDetachBanner();
    }

    public void resumeBannerAd(boolean placement) {
        if (adsPref.getAdStatus() && !adsPref.getIronSourceBannerId().equals("0")) {
            if (adsPref.getMainAds().equals(IRONSOURCE) || adsPref.getBackupAds().equals(IRONSOURCE)) {
                loadBannerAd(placement);
            }
        }
    }

    public void updateConsentStatus() {
        if (Config.LEGACY_GDPR) {
            legacyGDPR.updateLegacyGDPRConsentStatus(adsPref.getAdMobPublisherId(), Config.PRIVACY_POLICY_URL);
        } else {
            gdpr.updateGDPRConsentStatus();
        }
    }

    public void saveAds(AdsPref adsPref, Ads ads) {
        adsPref.saveAds(
                ads.ad_status,
                ads.main_ads,
                ads.backup_ads,
                ads.admob_publisher_id,
                ads.admob_banner_unit_id,
                ads.admob_interstitial_unit_id,
                ads.admob_native_unit_id,
                ads.admob_app_open_ad_unit_id,

                ads.applovin_banner_ad_unit_id,
                ads.applovin_interstitial_ad_unit_id,
                ads.applovin_native_ad_manual_unit_id,
                ads.applovin_app_open_ad_unit_id,
                ads.applovin_banner_zone_id,
                ads.applovin_banner_mrec_zone_id,
                ads.applovin_interstitial_zone_id,

                ads.interstitial_ad_interval,
                ads.native_ad_index,
                ads.native_ad_style_post_list,
                ads.native_ad_style_post_details,
                ads.native_ad_style_exit_dialog
        );
    }

}
