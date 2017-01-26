package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Response;
import com.gighub.app.model.ResponseMusician;
import com.gighub.app.model.ServiceGighub;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAsMusicianActivity extends AppCompatActivity {

    private EditText mEditTextNameRegister, mEditTextEmailRegister, mEditTextPasswordRegister, mEditTextConfirmationPasswordRegister;
    private CheckBox mCheckBoxAgree;
    private Button mButtonRegister, mButtonCancelRegister;
    private SessionManager mSession;

    public static final String PESANLOG = "pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_musician);


        mCheckBoxAgree = (CheckBox)findViewById(R.id.cbx_agree_termcondition);
        mEditTextNameRegister = (EditText)findViewById(R.id.et_name_register_msc);
        mEditTextEmailRegister = (EditText)findViewById(R.id.et_email_register_msc);
        mEditTextPasswordRegister = (EditText)findViewById(R.id.et_password_register_msc);
        mEditTextConfirmationPasswordRegister = (EditText)findViewById(R.id.et_confirmation_password_register_msc);

        mButtonRegister = (Button)findViewById(R.id.btn_register_register_msc);
        mButtonCancelRegister = (Button)findViewById(R.id.btn_cancel_register_msc);

//        mButtonRegister.setFocusable(false);
//        mButtonRegister.setEnabled(false);
//

        mCheckBoxAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mCheckBoxAgree.isChecked()){
                    mButtonRegister.setFocusable(true);
                    mButtonRegister.setEnabled(true);
                }
                if(!mCheckBoxAgree.isChecked()){
                    mButtonRegister.setFocusable(false);
                    mButtonRegister.setEnabled(false);
                }
            }
        });


        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditTextPasswordRegister.getText().toString().equals(mEditTextConfirmationPasswordRegister.getText().toString())){
                    Toast.makeText(RegisterAsMusicianActivity.this, "Check your password", Toast.LENGTH_SHORT).show();
                }
                else {
                    insertMusician();

                }
            }
        });

        mButtonCancelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    Map<String,String> dataM = new HashMap<>();

    private void insertMusician(){
        mSession = new SessionManager(getApplicationContext());

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        dataM.put("name",mEditTextNameRegister.getText().toString());
        dataM.put("email",mEditTextEmailRegister.getText().toString());
        dataM.put("password",mEditTextPasswordRegister.getText().toString());
        dataM.put("firebase", FirebaseInstanceId.getInstance().getToken());

        buildUrl.serviceGighub.insertMusician(dataM).enqueue(new Callback<ResponseMusician>() {
            @Override
            public void onResponse(Call<ResponseMusician> call, retrofit2.Response<ResponseMusician> response) {
                if (response.code() == 200) {
                    mSession = new SessionManager(getApplicationContext());
                    mSession.createLoginSession(new Gson().toJson(response.body().getMusician()), "msc");
                    mSession.SkipIntro();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(RegisterAsMusicianActivity.this, "Berhasil Register", Toast.LENGTH_LONG).show();
                    Log.d(PESANLOG,"Berhasil Register " +response.code());
                    startActivity(intent);
                }
                else {
                    Log.d(PESANLOG, "Pesan Log : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseMusician> call, Throwable t) {
                Log.d(PESANLOG,"Gagal Register");
                Toast.makeText(RegisterAsMusicianActivity.this,"Gagal Register",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
