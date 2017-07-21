package com.accademy.harvin.harvinacademy.network;

import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.model.Subjects;
import com.accademy.harvin.harvinacademy.model.UserTest;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by ishank on 7/7/17.
 */

public interface RetrofitInterface {


    @POST("login")
    Observable<UserTest> login(@Body UserTest userTest);
    @POST("signup")
    Observable<UserTest> register(@Body UserTest usertest);
    @GET("subjects")
    Observable<Subjects> getSubjectList();
    @GET("files/abc")
    Call<ResponseBody> downloadfile();




}
