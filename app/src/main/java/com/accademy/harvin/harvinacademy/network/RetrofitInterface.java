package com.accademy.harvin.harvinacademy.network;

import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.model.Profile;
import com.accademy.harvin.harvinacademy.model.Subjects;
import com.accademy.harvin.harvinacademy.model.UserTest;
import com.accademy.harvin.harvinacademy.model.exam.QuestionTestPaper;
import com.accademy.harvin.harvinacademy.model.user.Progresses;
import com.accademy.harvin.harvinacademy.model.user.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;


/**
 * Created by ishank on 7/7/17.
 */

public interface RetrofitInterface {


    @POST("student/login")
    Observable<UserTest> login(@Body UserTest userTest);
    @POST("student/signup")
    Observable<User> register(@Body Profile mProfile);
    @GET("student/{username}/subjects")
    Observable<Subjects> getSubjectList(@Path("username")String username);
    @Streaming
    @GET("files/{name}")
    Call<ResponseBody> downloadFile(@Path("name")String topicId);
    @GET("student/{username}/progresses")
    Observable<Progresses> getProgress(@Path("username")String username);
    @GET("exams/{username}/exams/{questionPaperId}/questionPaper")
    Observable<QuestionTestPaper> getTestPaper(@Path("username")String username,@Path("questionPaperId")String questionPaperId);




}
