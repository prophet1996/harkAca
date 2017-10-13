package com.accademy.harvin.harvinacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BatchSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_select);
        setFullScreen();
        getBatchList();
        diplaytheBatchInfo();
        setnewBatchForTheUser();
        startNewActivityWithNewUserInfo();
    }

    private void setFullScreen() {
    }

    public void getBatchList() {
    }

    private void diplaytheBatchInfo() {
    }

    private void setnewBatchForTheUser() {
    }

    private void startNewActivityWithNewUserInfo() {
    }
}
