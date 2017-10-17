package com.accademy.harvin.harvinacademy;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.adapters.QuestionContentListAdapter;
import com.accademy.harvin.harvinacademy.adapters.QuestionNavigationListAdapter;
import com.accademy.harvin.harvinacademy.model.exam.QuestionPaper;
import com.accademy.harvin.harvinacademy.model.exam.QuestionTestPaper;
import com.accademy.harvin.harvinacademy.model.user.User;
import com.accademy.harvin.harvinacademy.network.HTTPclient;
import com.accademy.harvin.harvinacademy.network.RetrofitBuilder;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;
import com.accademy.harvin.harvinacademy.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.accademy.harvin.harvinacademy.utils.Constants.EXAM_ID_KEY;

public class McqActivity extends AppCompatActivity  {
    private String DUMMYID="59bcd16d2cb25f494b1fa2a9";
    private String currentExamId="59bcd16d2cb25f494b1fa2a9";
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
    private QuestionTestPaper questionTestPaper1;
    private PagerSnapHelper contentSnapHelper;

    //for timer
    private ProgressBar timerProgressBar;
    private CountDownTimer countDownTimer;
    private TextView timerTextView;
    private RelativeLayout timerLayout;
    private long timeCountInMilliSeconds = 1 * 60000;
    private int completedTracker[];
    private List<Integer> clickTracker;
    private TextView stateCompletedTextView;
    private TextView stateRevisionTextView;
    private TextView stateNotAttemptedTextView;
    private AppCompatButton submitTestButton;
    private AlertDialog progressDialog;
    private AlertDialog.Builder progressDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq);
        setFullScreen();
        navigation = findViewById(R.id.fragment_navigation_mcq);
        content = findViewById(R.id.layout_navigation_content);
        questionNumberTextView = findViewById(R.id.question_number_textview);

        recyclerViewNavigation = navigation.findViewById(R.id.layout_navigation_mcq_recyclerview);
        recyclerViewContent = content.findViewById(R.id.question_recyclerView_content);
        stateCompletedTextView = content.findViewById(R.id.state_value_completed);
        stateRevisionTextView = content.findViewById(R.id.state_value_revision);
        stateNotAttemptedTextView = content.findViewById(R.id.state_value_not_attempted);
        submitTestButton = content.findViewById(R.id.submit_test_button);


        Log.d("view model", "null");
        showdialdog();
        currentExamId = getIntent().getStringExtra(EXAM_ID_KEY);
        getTest();
        dismissdialog();
    }
    private void setFullScreen(){

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
        //setUIUpdateMethod();
        //start the timer
        setStateTextViewValues();
        setProgressBarValues();
        startTimer();



    }

    private void setStateTextViewValues() {
        stateNotAttemptedTextView.setText(questionTestPaper1.getQuestionPaper().getQuestions().size()+"");
        stateCompletedTextView.setText(Integer.toString(0));
        stateRevisionTextView.setText(Integer.toString(0));

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
                timerTextView.setText(Formatter.hmsTimeFormatter(0));
                showEndTestDialog();

            }
        };
        countDownTimer.start();
    }

    private void setTimerValues() {
//TODO add the time from the test data
        timeCountInMilliSeconds = 1 * 60 * 1000;


    }
    private void showEndTestDialog(){

    }

    @SuppressLint("NewApi")
    private void initViews() {
        timerLayout=content.findViewById(R.id.timer_layout);
        timerProgressBar=timerLayout.findViewById(R.id.timer_progess_bar);
        timerTextView=timerLayout.findViewById(R.id.timer_textiew);
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

        Observable<QuestionTestPaper> call=retrofitInterface.getTestPaper(User.getSharedUsername(McqActivity.this),currentExamId);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<QuestionTestPaper>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("testpaper","subscribe");}
                    @Override
                    public void onNext(@NonNull QuestionTestPaper questionTestPaper) {
                        if(questionTestPaper!=null){
                        Log.d("testpaper",questionTestPaper.getQuestionPaper().getId());
                            questionTestPaper1=questionTestPaper;
                            showtest(questionTestPaper);
                         //   saveTestToDb(questionTestPaper.getQuestionPaper());
                            }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("testpaper","error"+e.toString());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {Log.d("testpaper","complete");}
                });



    }
//    private void saveTestToDb(QuestionPaper questionPaper){
//
//        db=AppDatabase.getInMemoryDatabase(this);
//        DatabaseInitializer.populateAsyncQuestionPaper(db,questionPaper);
//
//         //   DatabaseInitializer.populateAsyncQuestion(db,questionPaper.getQuestions());
//
//
//    }
//TODO
    public void showtest(final QuestionTestPaper questionTestPaper){
        final QuestionPaper qp=questionTestPaper.getQuestionPaper();
        clickTracker= new ArrayList<>();
        completedTracker= new int[qp.getQuestions().size()];
        for(int i=0;i<qp.getQuestions().size();i++)
            clickTracker.add(0);

        questionNavigationListAdapter =new QuestionNavigationListAdapter(McqActivity.this,qp);

        questionNavigationListAdapter.setNavigationQuestionClickedListener(new QuestionNavigationListAdapter.NavigationQuestionClickedListener() {
            @Override
            public void onNavigationClicked(int position) {
                recyclerViewContent.smoothScrollToPosition(position);


            }

        });
     //   View currentChild=recyclerViewContent.getChildAt(linearLayoutManager.findFirstCompletelyVisibleItemPosition());
     //   if(currentChild!=null){}
        gridLayoutManager =new GridLayoutManager(this,2);
         recyclerViewNavigation.setLayoutManager(new LinearLayoutManager(this));
         recyclerViewNavigation.setAdapter(questionNavigationListAdapter);
        questionContentListAdapter=new QuestionContentListAdapter(McqActivity.this,qp);
        questionContentListAdapter.setMarkedForReviewListener(new QuestionContentListAdapter.MarkerForReviewListener() {
            @Override
            public void onMarked(int position,boolean checked) {
                if(checked){qp.getQuestions().get(position).isMarked=true;
                    stateRevisionTextView.setText((Integer.parseInt(stateRevisionTextView.getText().toString())+1)+"");
                }
                else {qp.getQuestions().get(position).isMarked=false;
                    stateRevisionTextView.setText((Integer.parseInt(stateRevisionTextView.getText().toString())-1)+"");

                }
            }
        });
        questionContentListAdapter.setQuestionSelectedListener(new QuestionContentListAdapter.QuestionSelectedListener() {
            @Override
            public void onQuestionClicked(int position) {
                clickTracker.set(position,clickTracker.get(position)+1);

                questionTestPaper.getQuestionPaper().getQuestions().get(position).isClicked=true;
                Toast.makeText(McqActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                Log.d("listener","clicked   10"+position);
                questionNavigationListAdapter.notifyItemChanged(position);
                if(clickTracker.get(position)==1){
                    stateCompletedTextView.setText((Integer.parseInt(stateCompletedTextView.getText().toString())+1)+"");
                    stateNotAttemptedTextView.setText((Integer.parseInt(stateNotAttemptedTextView.getText().toString())-1)+"");
                }



            }

            @Override
            public void onQuestionUnClicked(int position) {
                clickTracker.set(position,clickTracker.get(position)-1);
                Log.d("listener","unclicked out   10"+position);

                if(clickTracker.get(position)<=0){
                    Log.d("listener","unclicked   10"+position);
                    stateCompletedTextView.setText((Integer.parseInt(stateCompletedTextView.getText().toString())-1)+"");
                    stateNotAttemptedTextView.setText((Integer.parseInt(stateNotAttemptedTextView.getText().toString())+1)+"");



                    questionTestPaper.getQuestionPaper().getQuestions().get(position).isClicked=false;
                    questionNavigationListAdapter.notifyItemChanged(position);



                }

            }




        });
        recyclerViewContent.setHasFixedSize(true);
        recyclerViewContent.setAdapter(questionContentListAdapter);

        linearLayoutManager = new LinearLayoutManager(McqActivity.this,LinearLayoutManager.HORIZONTAL,true);
        recyclerViewContent.setLayoutManager(linearLayoutManager);
         contentSnapHelper= new PagerSnapHelper();

        contentSnapHelper.attachToRecyclerView(recyclerViewContent);
        totalQuestion=recyclerViewContent.getAdapter().getItemCount();
        questionNumberTextView.setText(Integer.toString(totalQuestion));
        submitTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAreYouSureDialdog();

            }
        });

        startTest();



    }

    private void showAreYouSureDialdog(){

        progressDialogBuilder=new AlertDialog.Builder(McqActivity.this);
        if(Integer.parseInt(stateNotAttemptedTextView.getText().toString())!=0)
            progressDialogBuilder.setMessage("ARE YOU SURE YOU WANT TO SUBMIT ?" +
                    "\nYou have "+stateNotAttemptedTextView.getText()+" questions left.");

        progressDialogBuilder.setTitle("Harvin Academy Test");
        progressDialogBuilder.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    checkTest();
                    finish();
                }
            });
        progressDialogBuilder.setNegativeButton( "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setFullScreen();

            }
        });
        progressDialogBuilder.setCancelable(false);
        progressDialog=progressDialogBuilder.create();

        setDialogFullScreen();

        progressDialog.show();



    }

    private void checkTest() {


    }

    private void setDialogFullScreen() {

        progressDialog.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setFullScreen();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
