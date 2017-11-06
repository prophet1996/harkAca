package com.accademy.harvin.harvinacademy.network;

import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.model.PAssignment;
import com.accademy.harvin.harvinacademy.model.Profile;
import com.accademy.harvin.harvinacademy.model.Subjects;
import com.accademy.harvin.harvinacademy.model.UserTest;
import com.accademy.harvin.harvinacademy.model.exam.ExamList;
import com.accademy.harvin.harvinacademy.model.exam.QuestionTestPaper;
import com.accademy.harvin.harvinacademy.model.exam.TestResult;
import com.accademy.harvin.harvinacademy.model.user.Batches;
import com.accademy.harvin.harvinacademy.model.user.Progresses;
import com.accademy.harvin.harvinacademy.model.user.User;
import com.accademy.harvin.harvinacademy.model.user.UserDetails;

import junit.framework.Test;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @GET("exams/{username}/exams")
    Observable<ExamList> getListOfExams(@Path("username")String username);
    //TODO
    @POST("student/loginWithEmail")
    Observable<UserDetails> loginWithEmail(@Body UserDetails userDetails);
    @GET("batches")
    Observable<Batches> getBatches();
    @PUT("student/{username}")
    Observable<UserDetails> setBatchForUser(@Path("username") String username,@Body UserDetails userDetails);
    @GET("assignment/{username}/assignments")
    Observable<PAssignment> getAssignmentList(@Path("username")String username);
    @GET("assignment/{assignmentId}")
    Call<ResponseBody> downloadAssignment(@Path("assignmentId") String assignmentId);
    @POST("exams/{questionPaperId}/{username}")
    Observable<TestResult> sendTestResult(@Path("questionPaperId")String questionPaperId,@Path("username")String username,@Body TestResult testResult);


}
