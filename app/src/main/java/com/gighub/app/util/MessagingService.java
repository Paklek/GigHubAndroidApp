package com.gighub.app.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.PenyewaanResponse;
import com.gighub.app.ui.activity.BookingListActivity;
import com.gighub.app.ui.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 19/01/2017.
 */
public class MessagingService extends FirebaseMessagingService {
    private Class<?> targetActivity;
    private SessionManager mSession;
    private int mIdUser;
    private String mTipeUser;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        mSession = new SessionManager(getApplicationContext());
        if (mSession.isLoggedIn()) {

             switch (remoteMessage.getData().get("type")) {
                case "booking":
                    targetActivity = BookingListActivity.class;
                    break;
                case "request":
                    targetActivity = BookingListActivity.class;
                    break;
            }
            pushNotification(remoteMessage);
        }
    }

    private void pushNotification(RemoteMessage data) {

        Intent intent = new Intent(this, targetActivity);
        if (mSession.checkUserType().equals("org")) {
            mIdUser = mSession.getUserDetails().getId();
            mTipeUser = "org";
        } else if (mSession.checkUserType().equals("msc")) {
            mIdUser = mSession.getMusicianDetails().getId();
            mTipeUser = "msc";
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Book(intent, mIdUser, mTipeUser);
//
        notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(data.getData().get("title"));
        notificationBuilder.setContentText(data.getData().get("body"));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.logo_v2);


        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


    }

    private void Book(final Intent i, int userId, String tipeUser) {
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
        Map<String, String> idUser = new HashMap<>();
        idUser.put("tipe_user", tipeUser);
        idUser.put("user_id", Integer.toString(userId));
        buildUrl.serviceGighub.sendIdUserForBook(idUser).enqueue(new Callback<PenyewaanResponse>() {
            @Override
            public void onResponse(Call<PenyewaanResponse> call, Response<PenyewaanResponse> response) {
                if (response.code() == 200) {
                    i.putExtra("onreq", new Gson().toJson(response.body().getPenyewaanList()));
                    i.putExtra("onproc", new Gson().toJson(response.body().getPenyewaanList()));
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_ONE_SHOT);
                    notificationBuilder.setContentIntent(pendingIntent);
                    notificationManager.notify(0, notificationBuilder.build());
                } else {
                    Log.d("response", response.message());
                    Toast.makeText(getApplicationContext(), "Booking list is empty", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PenyewaanResponse> call, Throwable t) {
                Log.d("fail", t.getCause().getMessage());
            }
        });
    }
}
