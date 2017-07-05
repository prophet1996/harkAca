package com.accademy.harvin.harvinacademy;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.accademy.harvin.harvinacademy.adapters.SubjectAdapter;
import com.accademy.harvin.harvinacademy.fragment.StudyFragment;

public class CourseActivity extends AppCompatActivity {
    private StudyFragment mStudyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Bundle b= getIntent().getExtras();
        Log.d("intent data",b.getString(SubjectAdapter.CHAPTER_KEY));
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mStudyFragment= new StudyFragment();
        replaceFragment(mStudyFragment,0);
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

    public void replaceFragment(StudyFragment sf, int pos){
        Bundle pos1=new Bundle();
        pos1.putInt("key",pos);
        sf.setArguments(pos1);
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction mFragmentTranscation=fragmentManager.beginTransaction();
        mFragmentTranscation.replace(R.id.fragment_container,sf);
        mFragmentTranscation.commit();
    }
}
