package com.accademy.harvin.harvinacademy.db.typeconvertor;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ishank on 6/10/17.
 */

public  class AnswersConverter {
    public static Gson gson;
    @TypeConverter
    public static List<String > toAnswer(String answersString){

        return answersString==null?null:Arrays.asList(answersString.split("\\s*,\\s*"));
    }
    @TypeConverter
    public static String fromAnswer


            (List<String> answers){
        return answers==null?null:TextUtils.join(",",answers);
    }

}
