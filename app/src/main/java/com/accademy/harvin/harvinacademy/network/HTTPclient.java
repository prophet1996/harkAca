package com.accademy.harvin.harvinacademy.network;

import android.content.Context;

import com.accademy.harvin.harvinacademy.MainActivity;
import com.accademy.harvin.harvinacademy.utils.Internet;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ishank on 17/9/17.
 */

public class HTTPclient {


    public static OkHttpClient getClient(final Context context){

        int cachesize=10*1024*1024;
        Cache cache=new Cache(context.getCacheDir(),cachesize);
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if(Internet.isAvailable(context)){
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

return okHttpClient;
    }
}
