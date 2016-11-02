package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Response;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class GigOfferMusicianItemActivity extends AppCompatActivity {

    private TextView mTextViewName, mTextViewGenre, mTextViewHarga, mTextViewTipe, mTextViewDeskripsi, mTextViewKota;
    private String mPhoto, mTipe, tanggal_mulai, tanggal_selesai;
    private int mMusicianOrGroupId, object_id, gig_id,user_id;
    private ImageView mImageViewPhoto;
    private Button mButtonSubmit;
    Context mContext;
    private SessionManager mSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_offer_musician);

        Intent intent = new Intent(getIntent());

        mSession = new SessionManager(getApplicationContext());
        mContext = getApplicationContext();
        mButtonSubmit = (Button)findViewById(R.id.btn_submit_gigoffermusician);

        mTextViewName = (TextView)findViewById(R.id.musician_name_gigoffermusician);
        mImageViewPhoto = (ImageView)findViewById(R.id.img_gigoffermusician);
        mTextViewGenre = (TextView)findViewById(R.id.musician_genres_gigoffermusician);
        mTextViewHarga = (TextView)findViewById(R.id.musician_fee_gigoffermusician);
        mTextViewTipe = (TextView)findViewById(R.id.tv_tipe_gigoffermusician);
        mTextViewDeskripsi = (TextView)findViewById(R.id.tv_deskripsi_gigoffermusician);
        mTextViewKota = (TextView)findViewById(R.id.tv_kota_gigoffermusician);

        mMusicianOrGroupId = intent.getIntExtra("id",0);
        object_id = intent.getIntExtra("object_id",0);
        gig_id = intent.getIntExtra("gig_id",0);
        mPhoto = intent.getStringExtra("photo");
        mTipe = intent.getStringExtra("tipe");
        user_id = mSession.getMusicianDetails().getId();
        tanggal_mulai = intent.getStringExtra("tanggal_mulai");
        tanggal_selesai = intent.getStringExtra("tanggal_selesai");

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        if(mPhoto !=null && !mPhoto.equals("")) {
            Picasso.with(this).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhoto)).into(mImageViewPhoto);
        }

        mTextViewName.setText(intent.getStringExtra("name"));
        mTextViewHarga.setText("Rp."+intent.getStringExtra("harga"));
        mTextViewGenre.setText(intent.getStringExtra("genrenya"));
        mTextViewTipe.setText("Tipe : "+ mTipe);
        mTextViewDeskripsi.setText("Deskripsi : "+intent.getStringExtra("deskripsi"));
        mTextViewKota.setText("Kota : "+intent.getStringExtra("kota"));

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOffer();
            }
        });
    }

    Map <String,String> submitData = new HashMap<>();
    private void sendOffer(){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        submitData.put("user_id",Integer.toString(user_id));
        submitData.put("gig_id",Integer.toString(gig_id));
        submitData.put("subject_id",Integer.toString(mMusicianOrGroupId));
        submitData.put("object_id",Integer.toString(object_id));
        submitData.put("tipe",mTipe);
        submitData.put("tanggal_mulai", tanggal_mulai);
        submitData.put("tanggal_selesai", tanggal_selesai);
        buildUrl.serviceGighub.sendOfferSubmitData(submitData).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.code()==200) {
                    Intent intent = new Intent(mContext, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(GigOfferMusicianItemActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                Log.d(CREATEBAND,"response " +response.code() +" "+ response.body().getMessage());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                Toast.makeText(GigOfferMusicianItemActivity.this, response.message(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}
