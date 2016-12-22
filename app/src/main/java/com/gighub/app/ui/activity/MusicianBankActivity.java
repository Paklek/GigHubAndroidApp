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
import com.gighub.app.model.Bank;
import com.gighub.app.model.BankResponse;
import com.gighub.app.model.Genre;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicianBankActivity extends AppCompatActivity {

    private EditText mEditTextNoRek, mEditTextNamaBank, mEditTextAtasNama;
    private List<Bank> bank;
    private List<Genre> mMusicianGenres;
    private List<Genre> mGenreList;
    private Button mButtonUpdateBank;
    private SessionManager mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician_bank);

        mSession = new SessionManager(getApplicationContext());
        Intent intent = getIntent();

        final Type type = new TypeToken<List<Bank>>(){}.getType();
        final Type type2 = new TypeToken<List<Genre>>(){}.getType();
        bank = new Gson().fromJson(intent.getStringExtra("bank"),type);
        mMusicianGenres = new Gson().fromJson(intent.getStringExtra("musiciangenres"),type2);
        mGenreList = new Gson().fromJson(intent.getStringExtra("genres"),type2);

        mEditTextNoRek = (EditText)findViewById(R.id.et_no_rek_musicianbank);
        mEditTextNamaBank = (EditText)findViewById(R.id.et_nama_bank_musicianbank);
        mEditTextAtasNama = (EditText)findViewById(R.id.et_atas_nama_musicianbank);

        mButtonUpdateBank = (Button)findViewById(R.id.btn_update_bank_musicianbank);

        if(bank.size()!=0) {
            mEditTextNoRek.setText(bank.get(0).getNo_rek());
            mEditTextAtasNama.setText(bank.get(0).getAtas_nama());
            mEditTextNamaBank.setText(bank.get(0).getNama_bank());
        }
        mButtonUpdateBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map <String, String> dataBank = new HashMap<String, String>();
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();

                dataBank.put("id", Integer.toString(mSession.getMusicianDetails().getId()));
                dataBank.put("no_rek",mEditTextNoRek.getText().toString());
                dataBank.put("atas_nama",mEditTextAtasNama.getText().toString());
                dataBank.put("nama_bank",mEditTextNamaBank.getText().toString());

                buildUrl.serviceGighub.sendDataForUpdateBank(dataBank).enqueue(new Callback<BankResponse>() {
                    @Override
                    public void onResponse(Call<BankResponse> call, Response<BankResponse> response) {
                        Intent intent1 = new Intent(MusicianBankActivity.this,ProfileActivity.class);
                        intent1.putExtra("musiciangenres",new Gson().toJson(mMusicianGenres));
                        intent1.putExtra("genres",new Gson().toJson(mGenreList));

                        Toast.makeText(MusicianBankActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("updatebank",response.code()+"");

                        startActivity(intent1);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<BankResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}
