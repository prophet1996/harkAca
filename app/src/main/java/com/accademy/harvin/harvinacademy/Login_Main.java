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
import com.accademy.harvin.harvinacademy.model.user.Batch;
import com.accademy.harvin.harvinacademy.model.user.UserDetails;
import com.accademy.harvin.harvinacademy.network.HTTPclient;
import com.accademy.harvin.harvinacademy.network.RetrofitBuilder;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;
import com.accademy.harvin.harvinacademy.utils.Constants;
import com.accademy.harvin.harvinacademy.utils.SharedPref;
import com.accademy.harvin.harvinacademy.utils.Validation;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.accademy.harvin.harvinacademy.utils.Constants.BATCH_KEY;
import static com.accademy.harvin.harvinacademy.utils.Constants.PASSWORD_KEY;
import static com.accademy.harvin.harvinacademy.utils.Constants.USERNAME_KEY;


public class Login_Main extends AppCompatActivity {
    private static final String TAG = "RxAndroidSamples";
    private static final int REQUEST_REGISTER=100;
    private static final int APP_RESULT_CODE=101;

    private Observable<UserTest> call=null;



    Button b1;
    TextView username;
    TextView mRegister;
    TextView password;
    UserDetails userD;
    Button accountKitButton;
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
        b1 =  findViewById(R.id.login);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        mRegister= findViewById(R.id.register);
        accountKitButton=findViewById(R.id.account_kit_email_login);

        mRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i= new Intent(Login_Main.this,RegisterActivity.class);
              startActivityForResult(i,APP_RESULT_CODE);
            }
        });
        accountKitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onSMSLoginFlow();
            }
        });
        if(sharedPreferences.getString("username","z").equals("z")&&sharedPreferences.getString("password","z").equals("z")){

        }

        else{
               if(SharedPref.getStringPref(this,BATCH_KEY).equals('z')){
                Intent i= new Intent(Login_Main.this, BatchSelectActivity.class);
                startActivity(i);
                finish();
            }
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

    private void onSMSLoginFlow() {
        final Intent intent= new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder accountKitConfigurationBuilder=
                new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.EMAIL, AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,accountKitConfigurationBuilder.build());
        startActivityForResult(intent,APP_RESULT_CODE);
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
        editor.putString(PASSWORD_KEY,password.getText().toString());
        editor.putString(USERNAME_KEY,username.getText().toString());
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
                // Toast.makeText(this,"Something went wrong please try again..",Toast.LENGTH_SHORT).show();
            }}

            else if(requestCode==APP_RESULT_CODE){
                AccountKitLoginResult loginResult=data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
                String toastMessage="";
                Log.d("AccountKit","resultcode ok");

                if(loginResult.getError()!=null){
                    toastMessage=loginResult.getError().getErrorType().getMessage();

                }else if (loginResult.wasCancelled()){
                    toastMessage="Login Cancelled";
                    Log.d("AccountKit","login cancelled");

                }
                else {
                    if(loginResult.getAccessToken()!=null){
                        toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                        getAccount();
                    }
                }

                Toast.makeText(
                        this,
                        toastMessage,
                        Toast.LENGTH_LONG)
                        .show();
            }
            }


    private void getAccount() {
        Log.d("AccountKit","getAccount");
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                String accountKitId=account.getId();
                String accountKitEmail=account.getEmail();
                OkHttpClient client=HTTPclient.getClient(Login_Main.this);
                Retrofit retrofit=RetrofitBuilder.getRetrofit(Login_Main.this,client);
                RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);

                //TODO

                UserDetails currUser=new UserDetails();
                currUser.setUsername(accountKitEmail);
                Observable<UserDetails> call=retrofitInterface.loginWithEmail(currUser);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UserDetails>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d("AccountKit","got user data using email");

                            }

                            @Override
                            public void onNext(@NonNull UserDetails userDetails) {
                                Log.d("AccountKit","got user data using email");
                                userD=userDetails;
                                SharedPref.setPref(Login_Main.this,USERNAME_KEY,userDetails.getUsername());
                                SharedPref.setPref(Login_Main.this,PASSWORD_KEY,userDetails.getPassword());
                                SharedPref.setPref(Login_Main.this,BATCH_KEY,userDetails.getBatch());

                                if(userDetails.getBatch().isEmpty()){
                                    //TODO Start the batch select activity
                                    Intent i=new Intent(Login_Main.this,BatchSelectActivity.class);
                                    startActivity(i);
                                    finish();


                                }
                                else {
                                    //TODO Start Main Activity
                                    Intent i=new Intent(Login_Main.this,MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("AccountKit","got error on user data using email");


                            }

                            @Override
                            public void onComplete() {
                                Log.d("AccountKit","done with this observable ");


                            }
                        });
                Toast.makeText(Login_Main.this,accountKitId+" / "+accountKitEmail,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onError(AccountKitError accountKitError) {
                Log.e("AccountKit",accountKitError.toString());
            }
        });
    }

}
