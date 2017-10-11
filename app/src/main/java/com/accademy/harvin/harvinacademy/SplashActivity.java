package com.accademy.harvin.harvinacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.accademy.harvin.harvinacademy.utils.SharedPref;

import static com.accademy.harvin.harvinacademy.utils.Constants.FIRST_TIME_LOGIN;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean firsttime=SharedPref.getBooleanPref(this,FIRST_TIME_LOGIN);
        Intent i;
        Log.d("splash",firsttime+"");
        if(firsttime){
            Log.d("splash",firsttime+"what");

             i= new Intent(this,OnBoardingActivity.class);
            startActivity(i);

        }   else {

            Log.d("splash", "onCreate");
            i= new Intent(this, Login_Main.class);
            Log.d("splash", "sleeping");
            startActivity(i);

        }
        finish();
    }
}
