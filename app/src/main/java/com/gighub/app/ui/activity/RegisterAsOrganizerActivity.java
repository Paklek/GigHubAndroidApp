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
import com.gighub.app.model.ServiceGighub;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAsOrganizerActivity extends AppCompatActivity {

    private EditText mEditTextFirstNameRegisterOrg, mEditTextLastNameRegisterOrg, mEditTextEmailRegisterOrg, mEditTextPasswordRegisterOrg,mEditTextConfirmationPasswordOrg;
    private Button mButtonRegisterRegisterOrg, mButtonCancelRegisterOrg;
    private SessionManager mSession;

    public static final String PESANLOG = "pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_organizer);

        mEditTextFirstNameRegisterOrg = (EditText)findViewById(R.id.et_first_name_register_org);
        mEditTextLastNameRegisterOrg = (EditText)findViewById(R.id.et_last_name_register_org);
        mEditTextEmailRegisterOrg = (EditText)findViewById(R.id.et_email_register_org);
        mEditTextPasswordRegisterOrg = (EditText)findViewById(R.id.et_password_register_org);
        mEditTextConfirmationPasswordOrg = (EditText)findViewById(R.id.et_confirmation_password_register_org);

        mButtonCancelRegisterOrg = (Button)findViewById(R.id.btn_cancel_register_org);
        mButtonRegisterRegisterOrg = (Button)findViewById(R.id.btn_register_register_org);

        mButtonRegisterRegisterOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOrganizer();
            }
        });
        mButtonCancelRegisterOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    Map<String, String> dataO = new HashMap<>();

    private void insertOrganizer(){
        mSession = new SessionManager(getApplicationContext());

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        dataO.put("first_name",mEditTextFirstNameRegisterOrg.getText().toString());
        dataO.put("last_name",mEditTextLastNameRegisterOrg.getText().toString());
        dataO.put("email",mEditTextEmailRegisterOrg.getText().toString());
        dataO.put("password",mEditTextPasswordRegisterOrg.getText().toString());
        dataO.put("firebase", FirebaseInstanceId.getInstance().getToken());

        buildUrl.serviceGighub.insertOrganizer(dataO).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.code() == 200) {
                    mSession.SkipIntro();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(RegisterAsOrganizerActivity.this, "Berhasil Register", Toast.LENGTH_LONG).show();
                    Log.d(PESANLOG,"Berhasil Register " +response.code());
                    startActivity(intent);
                }
                else {
                    Log.d(PESANLOG, "Pesan Log : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(PESANLOG,"Gagal Register");
                Toast.makeText(RegisterAsOrganizerActivity.this, "Gagal Register",Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
