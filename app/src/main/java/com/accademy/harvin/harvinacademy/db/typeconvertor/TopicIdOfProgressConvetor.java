package com.accademy.harvin.harvinacademy.db.typeconvertor;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ishank on 16/10/17.
 */

public class TopicIdOfProgressConvetor {
    @TypeConverter
    public static List<String > toTopicIds(String answersString){

        return answersString==null?null: new ArrayList(Arrays.asList(answersString.split("\\s*,\\s*")));
    }
    @TypeConverter
    public static String fromTopicIds(List<String> answers){
        return answers==null?null: TextUtils.join(",",answers);
    }

}
