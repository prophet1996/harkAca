package com.accademy.harvin.harvinacademy;

import android.app.Application;
import android.content.Context;
//import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.accountkit.AccountKit;

/**
 * Created by ishank on 28/6/17.
 */

public class MyApplication extends Application {

    private static Context mContext;

    public static MyApplication instace;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        instace = this;
        AccountKit.initialize(getApplicationContext(), new AccountKit.InitializeCallback() {
            @Override
            public void onInitialized() {
                Log.d("Account kit","Initiialised");
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    //    MultiDex.install(this);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static MyApplication getInstance() {
        return instace;
    }
}

