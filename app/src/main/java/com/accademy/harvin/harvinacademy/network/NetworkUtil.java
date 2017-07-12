package com.accademy.harvin.harvinacademy.network;

import com.accademy.harvin.harvinacademy.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by ishank on 7/7/17.
 */

public class NetworkUtil {
    public static RetrofitInterface getRetrofit(){
        RxJavaCallAdapterFactory rxAdapter= RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);

    }
}
