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

    private EditText editTextNameRegister, editTextEmailRegister, editTextPasswordRegister, editTextConfirmationPasswordRegister;

    private Button buttonRegister;
    private SessionManager session;

    public static final String PESANLOG = "pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_musician);

        editTextNameRegister = (EditText)findViewById(R.id.et_name_register_msc);
        editTextEmailRegister = (EditText)findViewById(R.id.et_email_register_msc);
        editTextPasswordRegister = (EditText)findViewById(R.id.et_password_register_msc);
        editTextConfirmationPasswordRegister = (EditText)findViewById(R.id.et_confirmation_password_register_msc);

        buttonRegister = (Button)findViewById(R.id.btn_register_register_msc);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertMusician();
            }
        });
    }

    Map<String,String> dataM = new HashMap<>();

    private void insertMusician(){
        session = new SessionManager(getApplicationContext());

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        dataM.put("name",editTextNameRegister.getText().toString());
        dataM.put("email",editTextEmailRegister.getText().toString());
        dataM.put("password",editTextPasswordRegister.getText().toString());

        buildUrl.serviceGighub.insertMusician(dataM).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.code() == 200) {
                    session.SkipIntro();
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
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(PESANLOG,"Gagal Register");
                Toast.makeText(RegisterAsMusicianActivity.this,"Gagal Register",Toast.LENGTH_LONG).show();
            }
        });
    }

}
