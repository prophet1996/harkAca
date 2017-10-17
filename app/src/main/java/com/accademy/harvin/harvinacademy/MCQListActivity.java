package com.accademy.harvin.harvinacademy;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.accademy.harvin.harvinacademy.adapters.MCQListAdapter;
import com.accademy.harvin.harvinacademy.model.exam.Exam;
import com.accademy.harvin.harvinacademy.model.exam.ExamList;
import com.accademy.harvin.harvinacademy.network.HTTPclient;
import com.accademy.harvin.harvinacademy.network.RetrofitBuilder;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class MCQListActivity extends AppCompatActivity {
    private static final String TAG = "MCQLISTACTIVITY";
    private RecyclerView questionListRecyclerView;
    private List<Exam> examList;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqlist);

        username=getUsername();
        Log.d("getting progress",username);
        getExamList();
        questionListRecyclerView=findViewById(R.id.exam_list_recyclerView);

    }

    public void getExamList() {
        OkHttpClient okHttpClient= HTTPclient.getClient(this);

        Retrofit retrofit = RetrofitBuilder.getRetrofit(this,okHttpClient);
        RetrofitInterface client=retrofit.create(RetrofitInterface.class);
        Observable<ExamList> call=client.getListOfExams(username);
        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExamList>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ExamList exams) {
        Log.d("exams list",exams.getExams().size()+"");
                examList=exams.getExams();
                questionListRecyclerView.setLayoutManager(new LinearLayoutManager(MCQListActivity.this));
                questionListRecyclerView.setAdapter(new MCQListAdapter(examList,MCQListActivity.this));

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    public String getUsername() {
        String username;
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(MCQListActivity.this);

        username = sharedPreferences.getString("username", "z");
        Log.d("username",username);
        return username;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"ondestroy");
    }
}
