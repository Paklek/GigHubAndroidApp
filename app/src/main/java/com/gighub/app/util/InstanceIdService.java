package com.gighub.app.util;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Paklek on 17/01/2017.
 */
public class InstanceIdService  extends FirebaseInstanceIdService {
    private static final String REG_TOKEN = "FIREBASE_TOKEN";

    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN, recent_token);
    }
}