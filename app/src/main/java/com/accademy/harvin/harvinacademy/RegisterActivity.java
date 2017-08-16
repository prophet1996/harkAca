package com.accademy.harvin.harvinacademy;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.model.Profile;
import com.accademy.harvin.harvinacademy.model.UserTest;
import com.accademy.harvin.harvinacademy.model.user.User;
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
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private static String TAG=RegisterActivity.class.getSimpleName();

    private EditText re_username;
    private EditText re_password;
    private EditText re_conf_password;
    private EditText re_batch;
    private EditText re_contact;
    private EditText re_email;
    private EditText re_name;
    private Button register;
    private boolean[] fieldarray = new boolean[7];
    private boolean error=false;
    private Profile mProfile;
    private Observable<User> call=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findVBI();
        setclickListeners();

    }

    private void setclickListeners() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillprofile();
                registerprofile();

            }
        });
    }

    private void findVBI() {
        re_username=(EditText)findViewById(R.id.re_username);
        re_password=(EditText)findViewById(R.id.re_password);
        re_conf_password=(EditText)findViewById(R.id.re_conf_password);
        re_batch=(EditText)findViewById(R.id.re_batch);
        re_contact=(EditText)findViewById(R.id.re_contact);
        re_email=(EditText)findViewById(R.id.re_email);
        re_name=(EditText)findViewById(R.id.re_name);
        register=(Button)findViewById(R.id.re_register);
    }
    private void fillprofile(){

        fieldarray[0]=Validation.validataFields(re_username.getText().toString());
        fieldarray[1]=Validation.validataFields(re_password.getText().toString());
        fieldarray[2]=Validation.validataFields(re_conf_password.getText().toString());
        fieldarray[3]=Validation.validataFields(re_batch.getText().toString());
        fieldarray[4]=Validation.validataFields(re_contact.getText().toString());
        fieldarray[5]=Validation.validataFields(re_email.getText().toString());
        fieldarray[6]=Validation.validataFields(re_name.getText().toString());
        for(boolean a : fieldarray){
                if(!a)
                    error=true;

            }
        if(error){
            Toast.makeText(this,"Please check the fields",Toast.LENGTH_SHORT).show();
        }
        else{
            mProfile = new Profile(re_username.getText().toString(),
                    re_password.getText().toString(),
                    re_batch.getText().toString(),
                    re_contact.getText().toString(),
                    re_email.getText().toString(),
                    re_name.getText().toString()
                    );

        }
    }
    private void registerprofile() {
        HttpLoggingInterceptor loggin= new HttpLoggingInterceptor();

        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
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
        call = client.register(mProfile);
        call
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<User>() {


                    @Override
                    public void onNext(@NonNull User user) {
                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                            SharedPreferences.Editor editor=sharedPref.edit();
                            editor.putString("password",user.getUsername());
                            editor.putString("username",mProfile.getPassword());
                            Log.d("login","saved");
                            editor.commit();
                            setResult(RESULT_OK);
                            finish();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
