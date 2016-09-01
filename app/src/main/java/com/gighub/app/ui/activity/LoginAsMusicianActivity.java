package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.ResponseUser;
import com.gighub.app.model.ServiceGighub;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.StaticString;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAsMusicianActivity extends AppCompatActivity {

    private EditText editTextEmailLoginMsc, editTextPasswordLoginMsc;
    private Button btnCancelLoginMsc, btnLoginLoginMsc;
    private SessionManager session;

    public static final String PESANLOG = "pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_musician);

        editTextEmailLoginMsc = (EditText)findViewById(R.id.et_email_login_msc);
        editTextPasswordLoginMsc = (EditText)findViewById(R.id.et_password_login_msc);

        btnCancelLoginMsc = (Button)findViewById(R.id.btn_cancel_login_msc);
        btnLoginLoginMsc = (Button)findViewById(R.id.btn_login_login_msc);

        btnLoginLoginMsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginDataMusician();
            }
        });
    }
    Map<String, String> loginData = new HashMap<>();

    private void sendLoginDataMusician(){
//        Build URL
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
//        --------------
        loginData.put("email",editTextEmailLoginMsc.getText().toString());
        loginData.put("password",editTextPasswordLoginMsc.getText().toString());

        buildUrl.serviceGighub.sendLoginDataMusician(loginData).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if(response.code()==200){
                    if(response.body().getError()==0){
                        session = new SessionManager(getApplicationContext());
                        session.createLoginSession(response.body().getUser());
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        Log.d(PESANLOG,""+response.body().getMessage()+" error: "+response.body().getError());
                        Toast.makeText(LoginAsMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        session.SkipIntro();
                        startActivity(intent);
                    }
                }
                Log.d(PESANLOG,""+response.body().getMessage()+" error: "+response.body().getError());
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Log.d(PESANLOG,""+t.getMessage());
            }
        });
    }
}
