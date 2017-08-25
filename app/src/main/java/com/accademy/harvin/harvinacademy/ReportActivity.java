package com.accademy.harvin.harvinacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {
    Button reportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        reportButton=(Button)findViewById(R.id.report);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReportActivity.this,"Sent",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
