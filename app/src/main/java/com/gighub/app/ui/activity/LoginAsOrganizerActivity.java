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
import com.gighub.app.model.Response;
import com.gighub.app.model.ResponseUser;
import com.gighub.app.model.ServiceGighub;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAsOrganizerActivity extends AppCompatActivity {

    private EditText editTextEmailLoginOrg, editTextPasswordLoginOrg;
    private Button btnCancelLoginOrg, btnLoginLoginOrg;
    private SessionManager session;

    public final static String PESANLOG ="pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_organizer);

        editTextEmailLoginOrg = (EditText)findViewById(R.id.et_email_login_org);
        editTextPasswordLoginOrg = (EditText)findViewById(R.id.et_password_login_org);
        btnCancelLoginOrg = (Button)findViewById(R.id.btn_cancel_login_org);
        btnLoginLoginOrg = (Button)findViewById(R.id.btn_login_login_org);

        btnLoginLoginOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginDataOrganizer();
            }
        });
    }

    Map <String, String> loginData = new HashMap<>();

    private void sendLoginDataOrganizer(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.108.49.230/Gighub-master/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceGighub serviceGighub = retrofit.create(ServiceGighub.class);

        loginData.put("email", editTextEmailLoginOrg.getText().toString());
        loginData.put("password", editTextPasswordLoginOrg.getText().toString());

        serviceGighub.sendLoginDataOrganizer(loginData).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, retrofit2.Response<ResponseUser> response) {
         if(response.code()==200) {
             if (response.body().getError() == 0) {
                 session = new SessionManager(getApplicationContext());
                 session.createLoginSession(response.body().getUser());
                 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                 Log.d(PESANLOG, "" + response.body().getMessage());
                 Log.d(PESANLOG, "" + response.code());
                 Toast.makeText(LoginAsOrganizerActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                 session.SkipIntro();
                 startActivity(intent);
             }
         }
                Log.d(PESANLOG,""+response.body().getMessage());
                Log.d(PESANLOG,""+response.code());
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Log.d(PESANLOG,""+t.getMessage());
            }
        });
    }
}
