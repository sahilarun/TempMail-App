package com.sahil.tmailapp.Model;

import java.io.Serializable;

public class Ads implements Serializable {

    public boolean ad_status;
    public String main_ads = "";
    public String backup_ads = "";
    public String admob_publisher_id = "";
    public String admob_banner_unit_id = "";
    public String admob_interstitial_unit_id = "";
    public String admob_native_unit_id = "";
    public String admob_app_open_ad_unit_id = "";
    public String applovin_banner_ad_unit_id = "";
    public String applovin_interstitial_ad_unit_id = "";
    public String applovin_native_ad_manual_unit_id = "";
    public String applovin_app_open_ad_unit_id = "";
    public String applovin_banner_zone_id = "";
    public String applovin_banner_mrec_zone_id = "";
    public String applovin_interstitial_zone_id = "";

    public int interstitial_ad_interval;
    public int native_ad_index;

    public String native_ad_style_post_list = "";
    public String native_ad_style_post_details = "";
    public String native_ad_style_exit_dialog = "";


}
