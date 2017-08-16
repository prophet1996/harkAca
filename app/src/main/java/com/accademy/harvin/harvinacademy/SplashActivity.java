package com.accademy.harvin.harvinacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
Log.d("splash","onCreate");
        Intent i= new Intent(this,Login_Main.class);

            Log.d("splash","sleeping");
            startActivity(i);

     
        finish();
    }
}
