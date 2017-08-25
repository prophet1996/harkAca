package com.accademy.harvin.harvinacademy;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.fragment.ChapterFragment;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.model.Subject;
import com.google.gson.Gson;

import static com.accademy.harvin.harvinacademy.utils.Constants.SUBJECT_KEY;

public class CourseActivity extends AppCompatActivity {
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static final int PERMISSION_REQUEST_CODE = 1;


    private Chapter mChapter;
    private boolean downloadable=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

         Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSujectFromPrevActivity();
        replaceFragment(ChapterFragment.getInstance(mChapter,CourseActivity.this));
    registerReceiver();
        if(!checkPermission())
            requestPermission();

           }

    private void getSujectFromPrevActivity() {
        SharedPreferences mPref=getSharedPreferences(SUBJECT_KEY,MODE_PRIVATE);

        String gson=mPref.getString(SUBJECT_KEY,null);

        Gson GSON=new Gson();
        mChapter=GSON.fromJson(gson,Chapter.class);
        if(mChapter!=null)
            Log.d("donegson",""+mChapter.getChapterName());

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



    public void replaceFragment(ChapterFragment cf){
     Log.d("fragment","replaing fragments");
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction mFragmentTranscation=fragmentManager.beginTransaction();
        mFragmentTranscation.replace(R.id.fragment_container,cf);
        mFragmentTranscation.commit();
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
                {

                }
                Toast.makeText(CourseActivity.this,"downloading files now",Toast.LENGTH_SHORT).show();
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


}
