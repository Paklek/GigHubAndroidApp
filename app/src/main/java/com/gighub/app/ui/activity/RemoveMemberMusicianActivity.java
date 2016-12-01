package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Response;
import com.gighub.app.util.BuildUrl;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class RemoveMemberMusicianActivity extends AppCompatActivity {

    private Context mContext;
    private Button mButtonRemoveAnggota;
    private int mMusicianId, mGrupbandId;
    private String mNamaGrupband;
    private TextView mTextViewName,mTextViewPosition, mTextViewKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_anggota_musician);

        Intent intent = getIntent();
        mContext = getApplicationContext();
        mButtonRemoveAnggota = (Button)findViewById(R.id.btn_remove_anggota_removeanggotamusician);
        mTextViewName = (TextView)findViewById(R.id.tv_name_removeanggotamusician);
        mTextViewPosition = (TextView)findViewById(R.id.tv_position_removeanggotamusician);
        mTextViewKota = (TextView)findViewById(R.id.tv_kota_removeanggotamusician);


        mMusicianId = intent.getIntExtra("user_id",0);
        mGrupbandId = intent.getIntExtra("grupband_id", 0);
        mNamaGrupband = intent.getStringExtra("nama_grupband");

        mTextViewName.setText(intent.getStringExtra("name"));
        mTextViewPosition.setText(intent.getStringExtra("posisi")+" at "+mNamaGrupband);
        mTextViewKota.setText(intent.getStringExtra("kota"));


        mButtonRemoveAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();

                Map<String, String> sendRemove = new HashMap<String, String>();

                sendRemove.put("user_id",Integer.toString(mMusicianId));
                sendRemove.put("grupband_id",Integer.toString(mGrupbandId));

                buildUrl.serviceGighub.sendRemoveMusicianFromGroup(sendRemove).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.code()==200) {
                            Intent intent1 = new Intent(mContext, MainActivity.class);
                            Log.d("Code", "" + response.code());
                            Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
            }
        });

    }
}
