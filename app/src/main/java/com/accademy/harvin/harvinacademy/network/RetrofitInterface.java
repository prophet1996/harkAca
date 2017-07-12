package com.accademy.harvin.harvinacademy.network;

import com.accademy.harvin.harvinacademy.model.Response;
import com.accademy.harvin.harvinacademy.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ishank on 7/7/17.
 */

public interface RetrofitInterface {
    @POST("Users")
    Observable<Response> register(@Body User user);

    @POST("authenticate")
    Observable<Response> login();

    @GET ("users/{email}")
    Observable<User> getProfile(@Path("email") String email);
    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);



}
