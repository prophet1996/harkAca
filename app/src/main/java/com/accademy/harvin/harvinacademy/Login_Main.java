package com.accademy.harvin.harvinacademy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.exceptionHandler.DefaultExceptionHandler;
import com.accademy.harvin.harvinacademy.model.UserTest;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;
import com.accademy.harvin.harvinacademy.utils.Constants;
import com.accademy.harvin.harvinacademy.utils.Validation;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login_Main extends AppCompatActivity {
    private static final String TAG = "RxAndroidSamples";
    private static final int REQUEST_REGISTER=100;

    private Observable<UserTest> call=null;



    Button b1;
    TextView username;
    TextView mRegister;
    TextView password;
    ProgressDialog progressDoalog;
    boolean done = false;


    @Override
    protected void onStart() {
        super.onStart();




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(Login_Main.this);
        setContentView(R.layout.activity_login__main);
       // Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(Login_Main.this));
        b1 = (Button) findViewById(R.id.login);
        username = (TextView) findViewById(R.id.et_username);
        password = (TextView) findViewById(R.id.et_password);
        mRegister=(TextView) findViewById(R.id.register);

        mRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i= new Intent(Login_Main.this,RegisterActivity.class);
              startActivityForResult(i,REQUEST_REGISTER);
            }
        });
        if(sharedPreferences.getString("username","z").equals("z")&&sharedPreferences.getString("password","z").equals("z")){

        }else{
            Log.d("login","from saved");
            done=true;
            logindone();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("button clicked", "OK");
                login();





            }
        });


    }

    private void showdailog() {
        progressDoalog =new ProgressDialog(Login_Main.this);
        progressDoalog.setIndeterminate(true);
        progressDoalog.setMessage("Loggin in....");
        progressDoalog.setTitle("Harvin Academy Login");
        progressDoalog.setCancelable(false);
        progressDoalog.show();

    }

    private void login() {
        int err=0;
        if(!Validation.validateEmail(username.getText().toString())){
            showSnackBarMessage("Please try again..");
            err=1;
        }
        if(!Validation.validataFields(password.getText().toString())){
            showSnackBarMessage("Something went wrong..");
            err=1;
        }
        if(err==0){
           //Client side filter done
            //start the dialog
            showdailog();

            sendRequest(new UserTest(username.getText().toString(), password.getText().toString()));

        }

    }

    private void sendRequest(final UserTest user) {
        HttpLoggingInterceptor loggin= new HttpLoggingInterceptor();
        int cachesize=10*1024*1024;
        Cache cache=new Cache(getCacheDir(),cachesize);
        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .cache(cache)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        RetrofitInterface client = retrofit.create(RetrofitInterface.class);
         call = client.login(user);
        call
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserTest>() {


                    @Override
                    public void onNext(@NonNull UserTest userTest) {

                        if(user.getUsername().equals(userTest.getUsername()))
                        {
                        done = true;
                        saveuser();
                        logindone();}
                        else {progressDoalog.dismiss();
                            Toast.makeText(Login_Main.this,"Wrong username / Password",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError()", e);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                       progressDoalog.dismiss();
                            Toast.makeText(Login_Main.this,"cant login check your internet",Toast.LENGTH_SHORT).show();
                        }
                    },1000);

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void logindone() {
        if (done) {
            try{
            progressDoalog.dismiss();}
            catch (NullPointerException e){
                e.printStackTrace();

            }

            Intent i= new Intent(Login_Main.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    private void saveuser(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Login_Main.this);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putString("password",password.getText().toString());
        editor.putString("username",username.getText().toString());
        Log.d("login","saved");
        editor.commit();

    }
    private void showSnackBarMessage(String message){
        Toast.makeText(Login_Main.this,message,Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_REGISTER){
            if(resultCode==RESULT_OK){
                done=true;
                saveuser();
                logindone();
            }
            else {
                Toast.makeText(this,"Something went wrong please try again..",Toast.LENGTH_SHORT).show();
            }
        }
    }
}