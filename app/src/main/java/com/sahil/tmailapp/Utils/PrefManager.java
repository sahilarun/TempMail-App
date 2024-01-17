package com.sahil.tmailapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {


    private static final String PREF_NAME = "status_app";
    String TAG_NIGHT_MODE = "nightmode";
    Context mContext;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public PrefManager(Context context) {
        this.mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        this.pref = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public void saveGif(String str, String str2) {
        this.editor.putString("path", str);
        this.editor.putString("gif_name", str2);
        this.editor.apply();
    }

    public String getPath() {
        return this.pref.getString("path", "0");
    }

    public String getGifName() {
        return this.pref.getString("gif_name", "0");
    }


    public void updateDisplayPosition(int i) {
        this.editor.putInt("display_position", i);
        this.editor.apply();
    }


    public void setBoolean(String str, Boolean bool) {
        this.editor.putBoolean(str, bool.booleanValue());
        this.editor.commit();
    }

    public void setString(String str, String str2) {
        this.editor.putString(str, str2);
        this.editor.commit();
    }

    public void setInt(String str, int i) {
        this.editor.putInt(str, i);
        this.editor.commit();
    }

    public boolean getBoolean(String str) {
        return this.pref.getBoolean(str, true);
    }

    public void remove(String str) {
        if (this.pref.contains(str)) {
            this.editor.remove(str);
            this.editor.commit();
        }
    }

    public String getString(String str) {
        return this.pref.contains(str) ? this.pref.getString(str, null) : "";
    }

    public int getInt(String str) {
        return this.pref.getInt(str, 3);
    }


    //save
    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor= pref.edit();
        editor.putBoolean("NightMode",state);
        editor.commit();
    }

    //load
    public Boolean loadNightModeState(){
        Boolean state = pref.getBoolean("NightMode",true);
        return state;
    }

}
