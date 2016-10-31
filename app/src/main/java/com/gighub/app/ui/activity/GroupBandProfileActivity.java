package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gighub.app.R;

public class GroupBandProfileActivity extends AppCompatActivity {

    private TextView mTextViewAnggota, mTextViewPosisi, mTextViewNamaBand, mTextViewKota, mTextViewHarga, mTextTipe, mTextViewBasis, mTextViewDeskripsi;
    private String mAnggota, mPosisi, mNamaGrupBand, mKota, mHarga, mTipe, mDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_band_profile);

        Intent intent = getIntent();
        mAnggota = intent.getStringExtra("anggota");
        mPosisi = intent.getStringExtra("posisi");
        mNamaGrupBand = intent.getStringExtra("nama_grupband");
        mHarga = Integer.toString(intent.getIntExtra("harga",0));
        mKota = intent.getStringExtra("kota");
        mTipe = intent.getStringExtra("tipe");
        mDeskripsi = intent.getStringExtra("deskripsi");

        mTextViewAnggota = (TextView)findViewById(R.id.tv_anggota_grupbandprofileactivity);
        mTextViewPosisi = (TextView)findViewById(R.id.tv_posisi_grupbandprofileactivity);
        mTextViewNamaBand = (TextView)findViewById(R.id.tv_nama_grupband_grupbandprofileactivity);
        mTextViewKota = (TextView)findViewById(R.id.tv_kota_grupband_grupbandprofileactivity);
        mTextViewHarga = (TextView)findViewById(R.id.tv_harga_grupband_grupbandprofileactivity);
        mTextTipe = (TextView)findViewById(R.id.tv_genre_grupband_grupbandprofileactivity);
        mTextViewDeskripsi = (TextView)findViewById(R.id.tv_deskripsi_grupbandprofileactivity);

        mTextViewNamaBand.setText(mNamaGrupBand);
        mTextViewHarga.setText("Rp."+mHarga);
        mTextViewKota.setText(mKota);
        mTextTipe.setText(mTipe);
        mTextViewAnggota.setText("Anggota : "+mAnggota);
        mTextViewPosisi.setText("Posisi : "+mPosisi);
        mTextViewDeskripsi.setText(mDeskripsi);


    }
}
