package com.accademy.harvin.harvinacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MCQListActivity extends AppCompatActivity {
    private RecyclerView questionListRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqlist);
        questionListRecyclerView.setLayoutManager(new LinearLayoutManager(MCQListActivity.class));

    }
}
