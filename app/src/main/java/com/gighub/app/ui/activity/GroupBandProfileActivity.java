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
import com.gighub.app.model.MResponse;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupBandProfileActivity extends AppCompatActivity {

    private TextView mTextViewAnggota, mTextViewPosisi, mTextViewNamaBand, mTextViewKota, mTextViewHarga, mTextTipe, mTextViewBasis, mTextViewDeskripsi;
    private String mAnggota, mPosisi, mNamaGrupBand, mKota, mHarga, mTipe, mDeskripsi;
    private Button mButtonAddAnggota, mButtonRemoveAnggota;
    private SessionManager mSession;
    private Context mContext;
    private int mGrupBandId, mAdminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_band_profile);

        final Intent intent = getIntent();

        mContext = getApplicationContext();
        mSession = new SessionManager(mContext);

        mAnggota = intent.getStringExtra("anggota");
        mPosisi = intent.getStringExtra("posisi");
        mNamaGrupBand = intent.getStringExtra("nama_grupband");
        mHarga = Integer.toString(intent.getIntExtra("harga",0));
        mKota = intent.getStringExtra("kota");
        mTipe = intent.getStringExtra("tipe");
        mDeskripsi = intent.getStringExtra("deskripsi");
        mGrupBandId = intent.getIntExtra("grupband_id",0);
        mAdminId = intent.getIntExtra("admin_id",0);

        mTextViewAnggota = (TextView)findViewById(R.id.tv_anggota_grupbandprofileactivity);
        mTextViewPosisi = (TextView)findViewById(R.id.tv_posisi_grupbandprofileactivity);
        mTextViewNamaBand = (TextView)findViewById(R.id.tv_nama_grupband_grupbandprofileactivity);
        mTextViewKota = (TextView)findViewById(R.id.tv_kota_grupband_grupbandprofileactivity);
        mTextViewHarga = (TextView)findViewById(R.id.tv_harga_grupband_grupbandprofileactivity);
        mTextTipe = (TextView)findViewById(R.id.tv_genre_grupband_grupbandprofileactivity);
        mTextViewDeskripsi = (TextView)findViewById(R.id.tv_deskripsi_grupbandprofileactivity);

        mButtonAddAnggota = (Button)findViewById(R.id.btn_add_anggota_groupbandprofileactivity);
        mButtonRemoveAnggota = (Button)findViewById(R.id.btn_remove_anggota_groupbandprofileactivity);

        mTextViewNamaBand.setText(mNamaGrupBand);
        mTextViewHarga.setText("Rp."+mHarga);
        mTextViewKota.setText(mKota);
        mTextTipe.setText(mTipe);
        mTextViewAnggota.setText("Anggota : "+mAnggota);
        mTextViewPosisi.setText("Posisi : "+mPosisi);
        mTextViewDeskripsi.setText(mDeskripsi);

        if(mAdminId!=mSession.getMusicianDetails().getId()){
            mButtonAddAnggota.setVisibility(View.GONE);
        }


        mButtonAddAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();

                Map<String,String> viewMemberData = new HashMap<String, String>();

                viewMemberData.put("user_id",Integer.toString(mSession.getMusicianDetails().getId()));
                viewMemberData.put("grupband_id",Integer.toString(mGrupBandId));

                buildUrl.serviceGighub.sendForViewMember(viewMemberData).enqueue(new Callback<MResponse>() {
                    @Override
                    public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                        Intent intent1 = new Intent(GroupBandProfileActivity.this, AddMusicianToGroupActivity.class);
                        intent1.putExtra("calonanggota",new Gson().toJson(response.body().getMusicianans()));
                        intent1.putExtra("grupband_id",mGrupBandId);
                        intent1.putExtra("admin_id",mAdminId);
                        intent1.putExtra("nama_grupband",mNamaGrupBand);
//                        Toast.makeText(GroupBandProfileActivity.this,"Musician has been added", Toast.LENGTH_SHORT).show();
                        startActivity(intent1);
                    }

                    @Override
                    public void onFailure(Call<MResponse> call, Throwable t) {

                    }
                });
            }
        });

        mButtonRemoveAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();

                Map<String,String> viewMemberData = new HashMap<String, String>();

                viewMemberData.put("user_id",Integer.toString(mSession.getMusicianDetails().getId()));
                viewMemberData.put("grupband_id",Integer.toString(mGrupBandId));

                buildUrl.serviceGighub.sendForViewRemoveMember(viewMemberData).enqueue(new Callback<MResponse>() {
                    @Override
                    public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                        Intent intent1 = new Intent(GroupBandProfileActivity.this, RemoveMusicianFromGroupActivity.class);
                        intent1.putExtra("calonmantananggota",new Gson().toJson(response.body().getMusicianans()));
                        intent1.putExtra("grupband_id",mGrupBandId);
                        intent1.putExtra("admin_id",mAdminId);
                        intent1.putExtra("nama_grupband",mNamaGrupBand);
                        Log.d("response",response.body().toString());
                        startActivity(intent1);
                    }

                    @Override
                    public void onFailure(Call<MResponse> call, Throwable t) {

                    }
                });
            }
        });

    }
}
