package com.gighub.app.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Response;
import com.gighub.app.model.ResponseUser;
import com.gighub.app.model.ServiceGighub;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAsOrganizerActivity extends AppCompatActivity {

    private EditText mEditTextEmailLoginOrganizer, mEditTextPasswordLoginOrganizer;
    private Button mButtonCancelLoginOrganizer, mButtonLoginLoginOrganizer;
    private SessionManager mSession;
    private Context mContext;
    private TextView mTextViewRegister;

    public final static String PESANLOG ="pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_organizer);

        mContext = getApplicationContext();

        mEditTextEmailLoginOrganizer = (EditText)findViewById(R.id.et_email_login_org);
        mEditTextPasswordLoginOrganizer = (EditText)findViewById(R.id.et_password_login_org);
        mButtonCancelLoginOrganizer = (Button)findViewById(R.id.btn_cancel_login_org);
        mButtonLoginLoginOrganizer = (Button)findViewById(R.id.btn_login_login_org);
        mTextViewRegister = (TextView)findViewById(R.id.tv_register_login_org);

        mTextViewRegister.setPaintFlags(mTextViewRegister.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,RegisterAsOrganizerActivity.class);
                startActivity(intent);
            }
        });

        mButtonLoginLoginOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginDataOrganizer();
            }
        });
        mButtonCancelLoginOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    Map <String, String> loginData = new HashMap<>();

    private void sendLoginDataOrganizer(){

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        loginData.put("email", mEditTextEmailLoginOrganizer.getText().toString());
        loginData.put("password", mEditTextPasswordLoginOrganizer.getText().toString());
        loginData.put("firebase", FirebaseInstanceId.getInstance().getToken());

        buildUrl.serviceGighub.sendLoginDataOrganizer(loginData).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, retrofit2.Response<ResponseUser> response) {
                 if(response.code()==200) {
                     if (response.body().getError() == 0) {
                         mSession = new SessionManager(getApplicationContext());
                         mSession.createLoginSession(new Gson().toJson(response.body().getUser()),"org");
                         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                         Log.d(PESANLOG, "" + response.body().getMessage());
                         Log.d(PESANLOG, "" + response.code());
                         Toast.makeText(LoginAsOrganizerActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         mSession.SkipIntro();

                         mContext.startActivity(intent);
                     }
                 }
                Log.d(PESANLOG,""+response.body().getMessage());
                Log.d(PESANLOG,""+response.code());
                Toast.makeText(LoginAsOrganizerActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Log.d(PESANLOG,""+t.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
