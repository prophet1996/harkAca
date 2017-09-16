package com.accademy.harvin.harvinacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.exceptionHandler.DefaultExceptionHandler;

public class ReportActivity extends AppCompatActivity {
    Button reportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
      //  Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(ReportActivity.this));

        reportButton=(Button)findViewById(R.id.report);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReportActivity.this,"Thank you for your feedback.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
