package com.accademy.harvin.harvinacademy.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ishank on 7/8/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseInstIdService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,refreshedToken);
        sendRegistrationToServer(refreshedToken);


    }

    private boolean sendRegistrationToServer(String refreshedToken) {
    return true;
    }
}
