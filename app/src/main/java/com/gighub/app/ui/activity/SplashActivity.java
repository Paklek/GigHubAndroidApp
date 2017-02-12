package com.gighub.app.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.ResponseCallGenre;
import com.gighub.app.util.BuildUrl;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private String mGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
        buildUrl.serviceGighub.loadMusicianGenre().enqueue(new Callback<ResponseCallGenre>() {
            @Override
            public void onResponse(Call<ResponseCallGenre> call, Response<ResponseCallGenre> response) {
                Log.d("data log", response.code() + new Gson().toJson(response.body().getGenreList()));
                if (response.body().getError() == 0){
                    if (response.code() == 200) {
                        if (response.body().getError() == 0) {
//                        mListGenre = response.body().getGenreList();
                            mGenres = new Gson().toJson(response.body().getGenreList());
                            Intent _intent = new Intent(getApplicationContext(), MainActivity.class);
                            _intent.putExtra("genres", mGenres);
                            startActivity(_intent);
                            finish();
                        }
                    }
                    else {
                        AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this)
                                .setTitle("Connection Error")
                                .setMessage(R.string.failed_try_again+" "+response.message()+" "+response.code())
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        System.exit(0);
                                    }
                                })
                                .create();
                        alertDialog.show();
                    }
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this)
                            .setTitle("Connection Error")
                            .setMessage(R.string.failed_try_again+" "+response.message()+" "+response.code())
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    System.exit(0);
                                }
                            })
                            .create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCallGenre> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Connection Error")
                        .setMessage(R.string.failed_try_again)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

    }
}
