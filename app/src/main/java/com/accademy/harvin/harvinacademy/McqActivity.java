package com.accademy.harvin.harvinacademy;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.adapters.QuestionContentListAdapter;
import com.accademy.harvin.harvinacademy.adapters.QuestionNavigationListAdapter;
import com.accademy.harvin.harvinacademy.model.exam.QuestionTestPaper;
import com.accademy.harvin.harvinacademy.model.user.User;
import com.accademy.harvin.harvinacademy.network.HTTPclient;
import com.accademy.harvin.harvinacademy.network.RetrofitBuilder;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;
import com.accademy.harvin.harvinacademy.utils.Formatter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class McqActivity extends AppCompatActivity {
    private String DUMMYID="59bcd16d2cb25f494b1fa2a9";
    private ScrollView navigation;
    private RecyclerView recyclerViewContent;
    private RecyclerView recyclerViewNavigation;
    private QuestionNavigationListAdapter questionNavigationListAdapter;
    private QuestionContentListAdapter questionContentListAdapter;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private ConstraintLayout content;
    private TextView questionNumberTextView;
    private int totalQuestion=0;

    //for timer
    private ProgressBar timerProgressBar;
    private CountDownTimer countDownTimer;
    private TextView timerTextView;
    private RelativeLayout timerLayout;
    private long timeCountInMilliSeconds = 1 * 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        navigation=(ScrollView)findViewById(R.id.fragment_navigation_mcq);
        content=(ConstraintLayout) findViewById(R.id.layout_navigation_content);
        questionNumberTextView=(TextView)findViewById(R.id.question_number_textview);

         recyclerViewNavigation=(RecyclerView)navigation.findViewById(R.id.layout_navigation_mcq_recyclerview);
         recyclerViewContent=(RecyclerView)content.findViewById(R.id.question_recyclerView_content);


        showdialdog();
        getTest();
        startTest();
        dismissdialog();
    }
    private void setFullScreen(){

        setContentView(R.layout.activity_mcq);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }
    //TODO
    private void startTest() {
        // method call to initialize the timer views
        initViews();
        // method call to initialize the timer listeners
        initListeners();
        //start the timer
        setProgressBarValues();
        startTimer();


    }

    private void startTimer() {
        setTimerValues();
        startCountDownTimer();
    }

    private void startCountDownTimer() {
        countDownTimer=new CountDownTimer(timeCountInMilliSeconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(Formatter.hmsTimeFormatter(millisUntilFinished));
                Log.d("timer",""+millisUntilFinished);
                timerProgressBar.setProgress((int)millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                Toast.makeText(McqActivity.this,"done with the test ",Toast.LENGTH_SHORT);
                timerProgressBar.setProgress(100);

            }
        };
        countDownTimer.start();
    }

    private void setTimerValues() {
//TODO add the time from the test data
        timeCountInMilliSeconds = 1 * 60 * 1000;



    }

    @SuppressLint("NewApi")
    private void initViews() {
        timerLayout=(RelativeLayout)content.findViewById(R.id.timer_layout);
        timerProgressBar=(ProgressBar)timerLayout.findViewById(R.id.timer_progess_bar);
        timerTextView=(TextView)timerLayout.findViewById(R.id.timer_textiew);
        timerProgressBar.setIndeterminate(false);

    }
//TODO:listen to broadcast from service
    private void initListeners() {

    }
    /**
     * method to set circular progress bar values
     */
    private void setProgressBarValues() {

        timerProgressBar.setMax((int) timeCountInMilliSeconds / 1000);
        timerProgressBar.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    //TODO
    public void dismissdialog(){
    }
//TODO
    public void showdialdog() {
    }
//TODO
    public void getTest() {
        OkHttpClient client=HTTPclient.getClient(McqActivity.this);
        Retrofit retrofit= RetrofitBuilder.getRetrofit(McqActivity.this,client);
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        String username=User.getSharedUsername(McqActivity.this);
        Log.d("username1",username);

        Observable<QuestionTestPaper> call=retrofitInterface.getTestPaper(User.getSharedUsername(McqActivity.this),DUMMYID);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<QuestionTestPaper>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("testpaper","subscribe");

                    }

                    @Override
                    public void onNext(@NonNull QuestionTestPaper questionTestPaper) {
                        if(questionTestPaper!=null){
                        Log.d("testpaper",questionTestPaper.getQuestionPaper().getId());
                            showtest(questionTestPaper);

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("testpaper","error"+e.toString());
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {                        Log.d("testpaper","complete");


                    }
                })

        ;



    }
//TODO
    public void showtest(QuestionTestPaper questionTestPaper){

        questionNavigationListAdapter =new QuestionNavigationListAdapter(McqActivity.this,questionTestPaper.getQuestionPaper().getQuestions());
        gridLayoutManager =new GridLayoutManager(this,2);
         recyclerViewNavigation.setLayoutManager(gridLayoutManager);
         recyclerViewNavigation.setHasFixedSize(true);
         recyclerViewNavigation.setAdapter(questionNavigationListAdapter);
        questionContentListAdapter=new QuestionContentListAdapter(McqActivity.this,questionTestPaper.getQuestionPaper().getQuestions());
        recyclerViewContent.setHasFixedSize(true);
        recyclerViewContent.setAdapter(questionContentListAdapter);

        linearLayoutManager = new LinearLayoutManager(McqActivity.this,LinearLayoutManager.HORIZONTAL,true);
        recyclerViewContent.setLayoutManager(linearLayoutManager);
        SnapHelper contentSnapHelper= new PagerSnapHelper();
        contentSnapHelper.attachToRecyclerView(recyclerViewContent);
        totalQuestion=recyclerViewContent.getAdapter().getItemCount();
        questionNumberTextView.setText(Integer.toString(totalQuestion));




    }

    @Override
    protected void onResume() {
        super.onResume();
      //  setFullScreen();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setFullScreen();
    }
}
