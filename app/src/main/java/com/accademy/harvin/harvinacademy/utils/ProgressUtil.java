package com.accademy.harvin.harvinacademy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.accademy.harvin.harvinacademy.model.user.Progress;
import com.accademy.harvin.harvinacademy.model.user.Progresses;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.accademy.harvin.harvinacademy.utils.Constants.PROGRESS_KEY;

/**
 * Created by ishank on 5/9/17.
 */

public class ProgressUtil {
    private static Gson GSON;
    public static void saveProgress(Progresses progressList, Context context){
        GSON= new Gson();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(PROGRESS_KEY,GSON.toJson(progressList));
        editor.commit();

    }
    @Nullable
    public static List<Progress> getProgress(Context context){
        GSON= new Gson();
        List<Progress> progress=new ArrayList<>();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=preferences.edit();
        List<Progress> progressList=new ArrayList<>();
        for (Progress p:progressList){
        //p =GSON.fromJson(PROGRESS_KEY,Progress.class);

        }

        editor.commit();
return null;
    }
}
