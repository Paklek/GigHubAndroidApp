package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.MusicianSaldo;
import com.gighub.app.model.Response;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class WithdrawalActivity extends AppCompatActivity {

    private Context mContext;
    private int mMusicianId;
    private SessionManager mSession;
    private List<MusicianSaldo> musicianSaldos;
    private int totalSaldo = 0,mSaldoId, totalSaldoMusisi=0, totalSaldoBand=0;
    private TextView mTextViewSaldo, mTextViewSaldoMusisi,mTextViewSaldoBand;
    private EditText mEditTextJumlahWithdrawal;
    private Button mButtonSubmitWithdraw;
    private String mJumlahWithdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        mSession = new SessionManager(getApplicationContext());
        mContext = getApplicationContext();
        mMusicianId = mSession.getMusicianDetails().getId();

        Intent intent = getIntent();
        final Type type = new TypeToken<List<MusicianSaldo>>(){}.getType();
        musicianSaldos = new Gson().fromJson(intent.getStringExtra("musiciansaldos"),type);

        musicianSaldos.get(0).getId();

        mButtonSubmitWithdraw = (Button)findViewById(R.id.btn_submit_withdraw);
        mTextViewSaldo = (TextView)findViewById(R.id.tv_saldo_withdrawal);
        mTextViewSaldoMusisi = (TextView)findViewById(R.id.tv_your_musician_balance_withdrawal);
        mTextViewSaldoBand = (TextView)findViewById(R.id.tv_your_band_balance_withdrawal);
        mEditTextJumlahWithdrawal = (EditText)findViewById(R.id.et_jumlah_withdrawal);

        for (int i = 0; i<musicianSaldos.size();i++){
            if (musicianSaldos.get(i).getType_pemilik().equals("musisi")){
                totalSaldoMusisi+=musicianSaldos.get(i).getSaldo();
            }
            else {

                totalSaldoBand+=musicianSaldos.get(i).getSaldo();
            }
        totalSaldo+=musicianSaldos.get(i).getSaldo();
        }
        mSaldoId = musicianSaldos.get(0).getId();
        mTextViewSaldo.setText("Rp. "+totalSaldo);
        mTextViewSaldoMusisi.setText("Rp. "+totalSaldoMusisi);
        mTextViewSaldoBand.setText("Rp. "+totalSaldoBand);

        mButtonSubmitWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doWithdrawal();
            }
        });

    }

    Map<String, String> withdrwalData = new HashMap<>();
    private void doWithdrawal(){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        mJumlahWithdraw = mEditTextJumlahWithdrawal.getText().toString();

        withdrwalData.put("saldo_id",Integer.toString(mSaldoId));
        withdrwalData.put("jumlah",mJumlahWithdraw);


        buildUrl.serviceGighub.sendWithdrawData(withdrwalData).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Toast.makeText(WithdrawalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("error",t.getCause().getMessage());
            }
        });
    }
}
