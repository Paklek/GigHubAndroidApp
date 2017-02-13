package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class BookingMusicianDetails extends AppCompatActivity {
    private ImageView mImageViewMusicianPhoto, mImageViewGigPhoto;
    private Button mButtonSendRequest;
    private TextView mTextViewMusicianName, mTextViewHargaMusisiPerJam, mTextViewTotalHarga, mTextViewGenreMusisi, mTextViewtanggalMulai, mTextViewLocation, mTextViewNamagig;
    private int mTotalHarga;
    private String mUserId, mObjectId, mPhotoGig,mPhotoMusisi, mWaktuMulaiJoda,mWaktuSelesaiJoda, mWaktuMulai,mWaktuSelesai,mHarga,mTipe,mNamaGig, mLokasi, mLat,mLng;

    private SessionManager mSession;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_musician_details);

        mSession = new SessionManager(getApplicationContext());
        mContext = getApplicationContext();
        Intent intent = getIntent();

        mTipe = intent.getStringExtra("tipe");
        mUserId = intent.getStringExtra("user_id");
        mObjectId = intent.getStringExtra("object_id");
        mPhotoMusisi = intent.getStringExtra("photo");
        mPhotoGig = intent.getStringExtra("photo_gig");
        mWaktuMulai = intent.getStringExtra("tanggal_mulai");
        mWaktuSelesai = intent.getStringExtra("tanggal_selesai");
        mHarga = intent.getStringExtra("harga_sewa");
        mNamaGig = intent.getStringExtra("nama_gig");
        mLokasi = intent.getStringExtra("lokasi");
        mLat = intent.getStringExtra("lat");
        mLng = intent.getStringExtra("lng");

        mWaktuMulaiJoda = mWaktuMulai.replace(' ','T');
        mWaktuSelesaiJoda = mWaktuSelesai.replace(' ', 'T');

        DateTime startTime, endTime;
        startTime = DateTime.parse(mWaktuMulaiJoda);
        endTime = DateTime.parse(mWaktuSelesaiJoda);
        Period p = new Period(startTime,endTime);
        int totalHours = p.getHours();


        mImageViewMusicianPhoto = (ImageView)findViewById(R.id.tv_img_musician_bookingmusiciandetailsactivity);
        mImageViewGigPhoto = (ImageView)findViewById(R.id.img_photo_gig_bookingmusiciandetailsactivity);
        mButtonSendRequest = (Button)findViewById(R.id.btn_send_book_request_bookingmusiciandetailsactivity);
        mTextViewMusicianName = (TextView)findViewById(R.id.tv_musician_name_bookingmusiciandetailsactivity);
        mTextViewHargaMusisiPerJam = (TextView)findViewById(R.id.tv_musician_fee_bookingmusiciandetailsactivity);
        mTextViewTotalHarga = (TextView)findViewById(R.id.tv_total_harga_bookingmusiciandetailsactivity);
        mTextViewGenreMusisi = (TextView)findViewById(R.id.tv_musician_genres_bookingmusiciandetailsactivity);
        mTextViewtanggalMulai = (TextView)findViewById(R.id.tv_tanggal_main_bookingmusiciandetailsactivity);
        mTextViewLocation = (TextView)findViewById(R.id.tv_lokasi_gig_bookingmusiciandetailsactivity);
        mTextViewNamagig = (TextView)findViewById(R.id.tv_nama_gig_bookingmusiciandetailsactivity);


        mTotalHarga = Integer.parseInt(mHarga)*totalHours;

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        if(mPhotoMusisi!=null && !mPhotoMusisi.equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhotoMusisi)).into(mImageViewMusicianPhoto);
        }
        if(mPhotoGig!=null && !mPhotoGig.equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhotoGig)).into(mImageViewGigPhoto);
        }

        mTextViewMusicianName.setText(intent.getStringExtra("name"));
        mTextViewHargaMusisiPerJam.setText(intent.getStringExtra("harga_sewa"));
        mTextViewGenreMusisi.setText(intent.getStringExtra("genre"));
        mTextViewLocation.setText(mLokasi);
        mTextViewtanggalMulai.setText(intent.getStringExtra("tanggal_mulai"));
        mTextViewTotalHarga.setText("Rp. "+mTotalHarga+"");
//        intent.getStringExtra("");
//        intent.getStringExtra("");
//        intent.getStringExtra("");
//        intent.getStringExtra("");
//        intent.getStringExtra("");



        mButtonSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();
                Map<String, String> bookData = new HashMap<>();
                bookData.put("user_id",mUserId);
                bookData.put("tipe",mTipe);
                bookData.put("object_id",mObjectId);
                bookData.put("nama_gig",mNamaGig);
                bookData.put("lokasi", mLokasi);
                if(mPhotoGig!=null) {
                    if (!mPhotoGig.equals("") || mPhotoGig != null) {
                        bookData.put("photo_gig", mPhotoGig);
                    }
                }
                else {
                    bookData.put("photo_gig", "");
                }
                bookData.put("detail_lokasi",mLokasi);
                bookData.put("tanggal_mulai",mWaktuMulai);
                bookData.put("tanggal_selesai",mWaktuSelesai);
                bookData.put("lat",mLat);
                bookData.put("lng",mLng);

                buildUrl.serviceGighub.sendBookData(bookData).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                        if(response.code()==200) {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            Toast.makeText(BookingMusicianDetails.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("response ", response.code() + " " + response.body().getMessage());
                            Log.d("response ", response.code() + " " + response.message() +" "+response.code());

                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mContext.startActivity(intent);
                        }
                        else {
                            Log.d("response ", response.code() + " " + response.body().getMessage() +" "+response.code());
                            Log.d("Pesan Log : " , response.code()+" " + response.message());
                            Toast.makeText(BookingMusicianDetails.this, "Failed, Check Your Connection", Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Log.d("fail",t.getCause().getMessage());
                    }
                });
            }
        });

    }
}
