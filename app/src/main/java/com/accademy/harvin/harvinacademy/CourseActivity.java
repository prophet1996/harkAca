package com.accademy.harvin.harvinacademy;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.adapters.TopicAdapter;
import com.accademy.harvin.harvinacademy.db.AppDatabase;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.ChapterWithTopic;
import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.model.File;
import com.accademy.harvin.harvinacademy.model.Topic;

import java.util.List;

import static com.accademy.harvin.harvinacademy.utils.Constants.CHAPTER_KEY;

public class CourseActivity extends AppCompatActivity {
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static final int PERMISSION_REQUEST_CODE = 1;

    private  View chapterCard;
    private TextView chapterCardDesc;
    private TextView chapterCardTitle;
    private TextView chapterCardNo;
    private ProgressBar chapterCardProgressBar;
    private AppDatabase appDatabase;
    private List<ChapterWithTopic> chapterWithTopics;
    private String chapterId;
    private int chapterPosition=-1;
    private RecyclerView topicRecyclerView;
    private int chapterProgress=-1;
    private TopicAdapter topicAdapter;
    private List<Topic> topics;
    private List<File> files;
    private ProgressBar chapterProgressBar;
    private TextView chapterTextView;
    private Chapter currChapter;





    private boolean downloadable=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        //Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this));
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        chapterCard = findViewById(R.id.chapter_card);
        topicRecyclerView=findViewById(R.id.new_topic_recylerview);
        chapterProgressBar=findViewById(R.id.chapter_progress_progressbar);
        chapterProgressBar.setProgress(0);
        chapterTextView=findViewById(R.id.chapter_progress_textview);


        chapterCardTitle = chapterCard.findViewById(R.id.chapter_title);
        chapterCardNo = chapterCard.findViewById(R.id.chapter_no);
        chapterCardDesc = chapterCard.findViewById(R.id.exam_type);
        chapterCardProgressBar = chapterCard.findViewById(R.id.progress_bar_chapter);
        getChapter();

        getTopics();

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("chap With",""+chapterPosition);
        if(chapterWithTopics.get(chapterPosition).topics==null)
            Log.d("chap With ","null");
        else
            Log.d("chap With ","not null");

        topicAdapter= new TopicAdapter(topics,files,chapterPosition-1,chapterId,CourseActivity.this);
        Log.d("topics prob  2",topics.size()+"");
        topicAdapter.setTopics(topics);

        topicAdapter.setProgressCheckClickedListener(new TopicAdapter.ProgressCheckClickedListener() {
            @Override
            public void onProgressClicked(int position) {

                 chapterProgressBar.setProgress(chapterProgressBar.getProgress()+(100/topics.size()));
                chapterTextView.setText(chapterProgressBar.getProgress()+"%");
            }

            @Override
            public void onProgressUnclicked(int position) {
                chapterProgressBar.setProgress(chapterProgressBar.getProgress()-(100/topics.size()));
                 chapterTextView.setText(chapterProgressBar.getProgress()+"%");
            }
        });
        topicRecyclerView.setLayoutManager(new LinearLayoutManager(CourseActivity.this));
        topicRecyclerView.setAdapter(topicAdapter);
        registerReceiver();
        if (!checkPermission())
            requestPermission();

    }




    private void setChapterCard() {

        chapterCardTitle.setText(currChapter.getChapterName());
        chapterCardDesc.setText(currChapter.getChapterDescription());
        chapterCardNo.setText("0"+(++chapterPosition));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }





    private void registerReceiver(){
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(MESSAGE_PROGRESS)){

                DownloadedPdf download = intent.getParcelableExtra("download");
                if(download.getProgress()==100)
                {                Toast.makeText(CourseActivity.this,"Downloaded...",Toast.LENGTH_SHORT).show();


                }
            }
        }
    };
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(CourseActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            downloadable=true;
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(){

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CourseActivity.PERMISSION_REQUEST_CODE);

    }


    public void getChapter() {
        Intent i=getIntent();
        chapterId=i.getStringExtra(CHAPTER_KEY);
        chapterPosition=i.getIntExtra(CHAPTER_KEY+"pos",-1);try{
        chapterProgress=Integer.parseInt(i.getStringExtra(CHAPTER_KEY+"prog"));}
        catch (NumberFormatException nf){
            nf.printStackTrace();
        }
        Log.d("ishank room",chapterId+"");
        Log.d("ishank room",chapterPosition+"");
        Log.d("ishank room",chapterProgress+"");


    }

    public void getTopics() {
appDatabase=AppDatabase.getInMemoryDatabase(CourseActivity.this);
         currChapter= appDatabase.chapterModel().findChapterWithChapterId(chapterId);
        Log.d("ishnk chap",currChapter.getChapterName());
        chapterWithTopics=appDatabase.chapterModel().findChaptersWithTopic();
        topics=appDatabase.topicModel().getTopicWithChapterId(chapterId);
        files=appDatabase.fileModel().findAllFilesWithChapterId(chapterId);

        setChapterCard();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appDatabase.destroyInstance();
    }
}
