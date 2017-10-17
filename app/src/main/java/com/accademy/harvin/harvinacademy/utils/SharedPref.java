package com.accademy.harvin.harvinacademy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.accademy.harvin.harvinacademy.utils.Constants.FIRST_TIME_LOGIN;

/**
 * Created by ishank on 11/10/17.
 */

public class SharedPref {
    public static boolean getBooleanPref(Context context,String key){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key,true);

    }
    public static void setBooleanPref(Context context,String key,boolean value){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public static void setPref(Context context ,String key,String value){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor =preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static String getStringPref(Context context,String key){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return  preferences.getString(key,"z");
    }
}
