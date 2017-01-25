package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.MResponse;
import com.gighub.app.model.ResponseMusician;
import com.gighub.app.model.ResponseUser;
import com.gighub.app.model.ServiceGighub;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.StaticString;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAsMusicianActivity extends AppCompatActivity {

    private EditText mEditTextEmailLoginMusician, mEditTextPasswordLoginMusician;
    private Button mButtonCancelLoginMusician, mButtonLoginLoginMusician;
    private SessionManager mSession;
    private Context mContext;

    public static final String PESANLOG = "pesanlog";
    public static final String FIRST_NAME = "fname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_musician);

        mContext = getApplicationContext();

        mEditTextEmailLoginMusician = (EditText)findViewById(R.id.et_email_login_msc);
        mEditTextPasswordLoginMusician = (EditText)findViewById(R.id.et_password_login_msc);

        mButtonCancelLoginMusician = (Button)findViewById(R.id.btn_cancel_login_msc);
        mButtonLoginLoginMusician = (Button)findViewById(R.id.btn_login_login_msc);

        mButtonLoginLoginMusician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginDataMusician();
            }
        });

        mButtonCancelLoginMusician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                Toast.makeText(LoginAsMusicianActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }
    Map<String, String> loginData = new HashMap<>();

    private void sendLoginDataMusician(){
//        Build URL
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
//        --------------
        loginData.put("email",mEditTextEmailLoginMusician.getText().toString());
        loginData.put("password",mEditTextPasswordLoginMusician.getText().toString());
        loginData.put("firebase", FirebaseInstanceId.getInstance().getToken());

        buildUrl.serviceGighub.sendLoginDataMusician(loginData).enqueue(new Callback<ResponseMusician>() {
            @Override
            public void onResponse(Call<ResponseMusician> call, Response<ResponseMusician> response) {
                if(response.code()==200){
                    if(response.body().getError()==0){
                        mSession = new SessionManager(getApplicationContext());
                        mSession.createLoginSession(new Gson().toJson(response.body().getMusician()),"msc");
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        Log.d(PESANLOG,""+response.body().getMessage()+" error: "+response.body().getError());
                        Toast.makeText(LoginAsMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mSession.SkipIntro();
                        startActivity(intent);
                    }
                }
                Log.d(PESANLOG,""+response.body().getMessage());
                Log.d(PESANLOG,""+response.code());
                Toast.makeText(LoginAsMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseMusician> call, Throwable t) {

            }
        });

//        buildUrl.serviceGighub.sendLoginDataMusician(loginData).enqueue(new Callback<ResponseMusician>() {
//            @Override
//            public void onResponse(Call<ResponseMusician> call, Response<ResponseMusician> response) {
//                if(response.code()==200){
//                    if(response.body().getError()==0){
//                        mSession = new SessionManager(getApplicationContext());
//                        mSession.createLoginSession(new Gson().toJson(response.body().getMusician()),"msc");
//                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                        Log.d(PESANLOG,""+response.body().getMessage()+" error: "+response.body().getError());
//                        Toast.makeText(LoginAsMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        mSession.SkipIntro();
//                        startActivity(intent);
//                    }
//                }
//                Log.d(PESANLOG,""+response.body().getMessage()+" error: "+response.body().getError());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseMusician> call, Throwable t) {
//
//            }
//        });




//        buildUrl.serviceGighub.sendLoginDataMusician(loginData).enqueue(new Callback<ResponseMusician>() {
//            @Override
//            public void onResponse(Call<ResponseMusician> call, Response<ResponseMusician> response) {
//                if(response.code()==200){
//                    if(response.body().getError()==0){
//                        mSession = new SessionManager(getApplicationContext());
//                        mSession.createLoginSession(new Gson().toJson(response.body().getMusician()),"msc");
//                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                        Log.d(PESANLOG,""+response.body().getMessage()+" error: "+response.body().getError());
//                        Toast.makeText(LoginAsMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        mSession.SkipIntro();
//                        startActivity(intent);
//                    }
//                }
//                Log.d(PESANLOG,""+response.body().getMessage()+" error: "+response.body().getError());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseMusician> call, Throwable t) {
//
//            }
//        });

//        buildUrl.serviceGighub.sendLogin(loginData).enqueue(new Callback<MResponse>() {
//            @Override
//            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
//                if(response.code()==200){
//                    if(response.body().getError()==0){
//                        mSession = new SessionManager(getApplicationContext());
//                        mSession.createLoginSession(new Gson().toJson(response.body().getMusicianans()),"msc");
//                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                        Log.d(PESANLOG,""+response.body().getMessage()+" error: "+response.body().getError());
//                        Toast.makeText(LoginAsMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        mSession.SkipIntro();
//                        startActivity(intent);
//                    }
//                }
//                Log.d(PESANLOG,""+response.body().getMessage()+" error: "+response.body().getError());
//            }
//
//            @Override
//            public void onFailure(Call<MResponse> call, Throwable t) {
//
//            }
//        });




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
