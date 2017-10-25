package com.accademy.harvin.harvinacademy;

import android.*;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.accademy.harvin.harvinacademy.adapters.AssignmentAdapter;
import com.accademy.harvin.harvinacademy.model.PAssignment;
import com.accademy.harvin.harvinacademy.network.HTTPclient;
import com.accademy.harvin.harvinacademy.network.RetrofitBuilder;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;
import com.accademy.harvin.harvinacademy.utils.SharedPref;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.accademy.harvin.harvinacademy.utils.Constants.USERNAME_KEY;

public class AssignmentActivity extends AppCompatActivity {
    private static final String TAG="AssignmentActivity";
    private CollapsingToolbarLayout collapsingToolbarLayout=null;
    private String username="";
    private PAssignment pAssignment1;
    private RecyclerView recyclerView;
    private boolean downloadable=false;
    private AssignmentAdapter assignmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout= findViewById(R.id.collapsing_toolbar_layout);
        recyclerView=findViewById(R.id.assigment_recyclerView);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));

        getUsername();
        dynamicToolbarColor();
        getAssignmentList();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbarTextAppearence();
        if (!checkPermission())
            requestPermission();
    }


    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(AssignmentActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            downloadable=true;
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(){

        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, CourseActivity.PERMISSION_REQUEST_CODE);

    }

    private void getUsername() {
        username=SharedPref.getStringPref(this,USERNAME_KEY);
        Log.d(TAG,""+username);



    }

    private void dynamicToolbarColor() {
    }

    private void toolbarTextAppearence() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(android.R.style.TextAppearance_DeviceDefault_Small);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(android.R.style.TextAppearance_DeviceDefault_Medium);


    }

    public void getAssignmentList() {
        OkHttpClient okHttpClient=HTTPclient.getClient(this);
        Retrofit retrofit= RetrofitBuilder.getRetrofit(this,okHttpClient);
        RetrofitInterface client=retrofit.create(RetrofitInterface.class);
        Observable<PAssignment> call=client.getAssignmentList(username);
        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PAssignment>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {Log.d(TAG,"subscribed");}

                    @Override
                    public void onNext(@NonNull PAssignment pAssignment) {
                        Log.d(TAG,"next");
                        if(pAssignment!=null){
                        pAssignment1=pAssignment;
                            recyclerView.setAdapter(new AssignmentAdapter(pAssignment1.getAssignments(),AssignmentActivity.this));
                        }else{Log.d(TAG,"empty pAssignemnt object");}
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {Log.d(TAG,"Error");}

                    @Override
                    public void onComplete() {Log.d(TAG,"completed");}
                });
    }
}
