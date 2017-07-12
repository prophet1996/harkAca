package com.accademy.harvin.harvinacademy.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by ishank on 6/7/17.
 */

public class Validation {
    public static boolean validataFields(String name){

        if(TextUtils.isEmpty(name)){

            return false;
        }
        else
            return true;

    }
    public static boolean validateEmail(String email){

        if(TextUtils.isEmpty(email)|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false;
        }
        else return true;
    }
}
