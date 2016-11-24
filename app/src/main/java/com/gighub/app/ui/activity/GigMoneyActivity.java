package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.MusicianSaldo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GigMoneyActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private List<MusicianSaldo> musicianSaldos;
    private TextView mTextViewSaldo;
    private int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_money);

        musicianSaldos = new ArrayList<MusicianSaldo>();

        mTextViewSaldo = (TextView)findViewById(R.id.tv_saldo_gigmoney);

        Intent intent = getIntent();

        final Type type = new TypeToken<List<MusicianSaldo>>(){}.getType();
        musicianSaldos = new Gson().fromJson(intent.getStringExtra("musiciansaldos"),type);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("Gig Money");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (int i = 0; i<musicianSaldos.size();i++){
            total+=musicianSaldos.get(i).getSaldo();
        }

        mTextViewSaldo.setText("Rp. "+total);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
