package com.accademy.harvin.harvinacademy.network;

import android.content.Context;

import com.accademy.harvin.harvinacademy.utils.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ishank on 17/9/17.
 */

public class RetrofitBuilder {
    public static Retrofit getRetrofit(Context context, OkHttpClient okHttpClient){
        return   new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
