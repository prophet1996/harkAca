package com.accademy.harvin.harvinacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.accademy.harvin.harvinacademy.adapters.SpinnerAdapter;
import com.accademy.harvin.harvinacademy.model.UserTest;
import com.accademy.harvin.harvinacademy.model.user.Batch;
import com.accademy.harvin.harvinacademy.model.user.Batches;
import com.accademy.harvin.harvinacademy.model.user.UserDetails;
import com.accademy.harvin.harvinacademy.network.HTTPclient;
import com.accademy.harvin.harvinacademy.network.RetrofitBuilder;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;
import com.accademy.harvin.harvinacademy.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.accademy.harvin.harvinacademy.utils.Constants.BATCH_KEY;
import static com.accademy.harvin.harvinacademy.utils.Constants.PASSWORD_KEY;
import static com.accademy.harvin.harvinacademy.utils.Constants.USERNAME_KEY;

public class BatchSelectActivity extends AppCompatActivity {
    private static final String TAG="BatchSlctActivity.class";
    private Batches currBatches;
    private Spinner batchesSpinner;
    private AppCompatTextView batchesDescription;
    private AppCompatButton batchesButton;
    private UserDetails currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_select);
        batchesSpinner=findViewById(R.id.batches_spinner);
        batchesDescription=findViewById(R.id.batches_desc_textview);
        batchesButton=findViewById(R.id.batches_button);
        batchesButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                currUser= getUserDetailsObject();
                                                 if(!currUser.getBatch().isEmpty()){
                                                     OkHttpClient okHttPclient=HTTPclient.getClient(BatchSelectActivity.this);
                                                    Retrofit retrofit=RetrofitBuilder.getRetrofit(BatchSelectActivity.this,okHttPclient);
                                                     RetrofitInterface client=retrofit.create(RetrofitInterface.class);
                                                     Observable<UserDetails> call=client.setBatchForUser(currUser.getUsername(),currUser);
                                                     call.subscribeOn(Schedulers.newThread())
                                                             .observeOn(AndroidSchedulers.mainThread())
                                                             .subscribe(new Observer<UserDetails>() {
                                                                 @Override
                                                                 public void onSubscribe(@NonNull Disposable d) {

                                                                 }

                                                                 @Override
                                                                 public void onNext(@NonNull UserDetails userDetails) {
                                                                     if(!userDetails.getBatch().isEmpty()&&userDetails!=null) {
                                                                         SharedPref.setPref(BatchSelectActivity.this, BATCH_KEY, userDetails.getBatch());
                                                                            Intent i=new Intent(BatchSelectActivity.this,MainActivity.class);
                                                                         startActivity(i);
                                                                     }
                                                                     else
                                                                         Log.d(TAG,"null user");

                                                                 }

                                                                 @Override
                                                                 public void onError(@NonNull Throwable e) {

                                                                 }

                                                                 @Override
                                                                 public void onComplete() {

                                                                 }
                                                             });
                                                 }
                                             }
                                         }
        );
        setFullScreen();
        getBatchList();

        setnewBatchForTheUser();
        startNewActivityWithNewUserInfo();
    }

    private void setFullScreen() {
    }

    public void getBatchList() {
        OkHttpClient client=HTTPclient.getClient(this);
        Retrofit retrofit =RetrofitBuilder.getRetrofit(this,client);
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        Observable<Batches> call=retrofitInterface.getBatches();
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Batches>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Batches batches) {
                    if(batches!=null)
                        currBatches=batches;
                        diplaytheBatchInfo();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void diplaytheBatchInfo() {
        List<String> batchCategories= new ArrayList<>();
        for(Batch bat:currBatches.getBatches())
        batchCategories.add(bat.getBatchName());
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,batchCategories);
        batchesSpinner.setAdapter(spinnerAdapter);

        batchesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                batchesDescription.setText(currBatches.getBatches().get(i).getBatchDesc());
            if(currUser!=null)
                currUser.setBatch(currBatches.getBatches().get(i).getBatchName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setnewBatchForTheUser() {
    }

    private void startNewActivityWithNewUserInfo() {
    }

    public UserDetails getUserDetailsObject() {
        UserDetails user=new UserDetails();
        user.setUsername(SharedPref.getStringPref(BatchSelectActivity.this,USERNAME_KEY));
        user.setPassword(SharedPref.getStringPref(BatchSelectActivity.this,PASSWORD_KEY));
        user.setBatch(batchesSpinner.getSelectedItem().toString());
        return user;
    }
}
