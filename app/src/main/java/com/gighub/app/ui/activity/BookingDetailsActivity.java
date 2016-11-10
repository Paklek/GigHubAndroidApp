package com.gighub.app.ui.activity;

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
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class BookingDetailsActivity extends AppCompatActivity {

    private Button mButtonConfirmPayment, mButtonConfirmRequest;
    private TextView mTextViewNamaGig, mTextViewNamaMusisi, mTextViewLocation, mTextViewHarga, mTextViewWaktuMulai, mTextViewWaktuSelesai, mTextViewTotal, mTextViewNamaPenyewa, mTextViewStatus;
    private SessionManager mSession;
    private int mSewaId,mMusicianId, mOrganizerId,mAdminId;
    private ImageView mImgPhotoGig;
    private CircularImageView mImgPhotoMusician;
    private String mPhoto, mPhotoGig, mStatus, mStatusRequest, mTipeGig, mActivity,mTipeSewa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        mSession = new SessionManager(getApplicationContext());
        final Intent intent = getIntent();

        mTextViewNamaGig = (TextView)findViewById(R.id.tv_nama_gig_bookdetails);
        mTextViewNamaMusisi = (TextView)findViewById(R.id.tv_nama_musisi_bookdetails);
        mTextViewLocation = (TextView)findViewById(R.id.tv_location_bookdetails);
        mTextViewNamaPenyewa = (TextView)findViewById(R.id.tv_penyewa_bookdetails);
        mTextViewHarga = (TextView)findViewById(R.id.tv_harga_bookdetails);
        mTextViewWaktuMulai = (TextView)findViewById(R.id.tv_waktu_mulai_bookdetails);
        mTextViewWaktuSelesai = (TextView)findViewById(R.id.tv_waktu_selesai_bookdetails);
        mTextViewTotal = (TextView)findViewById(R.id.tv_total_bookdetails);
        mTextViewStatus = (TextView)findViewById(R.id.tv_status_bookdetails);

        mImgPhotoGig = (ImageView)findViewById(R.id.img_photo_gig_bookdetails);
        mImgPhotoMusician = (CircularImageView)findViewById(R.id.img_photo_bookdetails);

        mButtonConfirmPayment = (Button)findViewById(R.id.btn_konfirmasi_pembayaran_bookingdetails);
        mButtonConfirmRequest = (Button)findViewById(R.id.btn_konfirmasi_permintaan_bookingdetails);

        mSewaId = intent.getIntExtra("sewa_id",0);

        mStatus = intent.getStringExtra("status");
        mStatusRequest = intent.getStringExtra("status_request");
        mActivity = intent.getStringExtra("activity");
        mAdminId = intent.getIntExtra("admin_id",0);
        mTipeSewa = intent.getStringExtra("type_sewa");


        mTipeGig = intent.getStringExtra("type_gig");

        mButtonConfirmRequest.setVisibility(View.GONE);
        mButtonConfirmPayment.setVisibility(View.GONE);

        if(mSession.checkUserType().equals("org")){
            mOrganizerId = mSession.getUserDetails().getId();
        }
        else if(mSession.checkUserType().equals("msc")){
            mMusicianId = mSession.getMusicianDetails().getId();
        }

        if (mSession.checkUserType().equals("org") && mTipeGig.equals("sewa") && mActivity.equals("onrequestbooking")){
            mButtonConfirmRequest.setVisibility(View.GONE);
            mButtonConfirmPayment.setVisibility(View.GONE);
        }
        else if(mSession.checkUserType().equals("org") && mTipeGig.equals("post") && mActivity.equals("onrequestbooking")){
            mButtonConfirmRequest.setVisibility(View.VISIBLE);
            mButtonConfirmPayment.setVisibility(View.GONE);
        }
        else if(mSession.checkUserType().equals("msc") && mTipeGig.equals("sewa") && mActivity.equals("onrequestbooking") && (mAdminId==mMusicianId || mTipeSewa.equals("hiremusisi"))){
            mButtonConfirmRequest.setVisibility(View.VISIBLE);
            mButtonConfirmPayment.setVisibility(View.GONE);
        }
        else if(mSession.checkUserType().equals("msc") && mTipeGig.equals("post") && mActivity.equals("onrequestbooking")){
            mButtonConfirmRequest.setVisibility(View.GONE);
            mButtonConfirmPayment.setVisibility(View.GONE);
        }
        else if (mSession.checkUserType().equals("org") && mTipeGig.equals("sewa") && mActivity.equals("onproccessbooking") && mStatus.equals("0")){
            mButtonConfirmRequest.setVisibility(View.GONE);
            mButtonConfirmPayment.setVisibility(View.VISIBLE);
        }
        else if(mSession.checkUserType().equals("org") && mTipeGig.equals("post") && mActivity.equals("onproccessbooking")){
            mButtonConfirmRequest.setVisibility(View.GONE);
            mButtonConfirmPayment.setVisibility(View.VISIBLE);
        }
        else if(mSession.checkUserType().equals("msc") && mTipeGig.equals("sewa") && mActivity.equals("onproccessbooking")){
            mButtonConfirmRequest.setVisibility(View.GONE);
            mButtonConfirmPayment.setVisibility(View.GONE);
        }
        else if(mSession.checkUserType().equals("msc") && mTipeGig.equals("post") && mActivity.equals("onproccessbooking")){
            mButtonConfirmRequest.setVisibility(View.GONE);
            mButtonConfirmPayment.setVisibility(View.GONE);
        }

        if(mStatus.equals("0") && mStatusRequest.equals("0")){
            mTextViewStatus.setText("Status : Waiting to Confirm");
        }
        else if(mStatus.equals("0") && mStatusRequest.equals("1")){
            mTextViewStatus.setText("Status : Waiting to Paid");
        }
        else if(mStatus.equals("1") && mStatusRequest.equals("1")){
            mTextViewStatus.setText("Status : Waiting to Verified");
        }
        else if(mStatus.equals("2") && mStatusRequest.equals("1")){
            mTextViewStatus.setText("Status : Verified");
        }

        mTextViewNamaGig.setText(intent.getStringExtra("nama_gig"));
        mTextViewNamaMusisi.setText(intent.getStringExtra("nama_musisi"));
        mTextViewNamaPenyewa.setText("Renter : "+intent.getStringExtra("nama_user"));
        mTextViewLocation.setText(intent.getStringExtra("lokasi"));
        mTextViewWaktuMulai.setText(intent.getStringExtra("waktu_mulai"));
        mTextViewWaktuSelesai.setText(intent.getStringExtra("waktu_selesai"));
        mTextViewHarga.setText("Rp."+Integer.toString(intent.getIntExtra("harga_sewa",0)));
        mTextViewTotal.setText("Rp."+Integer.toString(intent.getIntExtra("total",0)));

        mPhoto = intent.getStringExtra("photo");
        mPhotoGig = intent.getStringExtra("photo_gig");

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();

        if(!mPhoto.equals("")) {
            Picasso.with(this).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhoto)).into(mImgPhotoMusician);
        }

        if(!mPhotoGig.equals("")){
            Picasso.with(this).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhotoGig)).into(mImgPhotoGig);
        }

//        if (intent.getIntExtra("harga_sewa",0)!=0){
//            mTextViewHarga.setText("Rp."+Integer.toString(intent.getIntExtra("harga_sewa",0)));
//        }
//        else if (intent.getIntExtra("harga_sewa",0)==0){
//            mTextViewHarga.setText("Rp."+Integer.toString(intent.getIntExtra("harga",0)));
//
//        }

//        if(mSession.checkUserType().equals("msc")){
//            mButtonConfirmPayment.setVisibility(View.GONE);
//        }
//        else{
//            mButtonConfirmRequest.setVisibility(View.GONE);
//        }

        mButtonConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),KonfirmasiPembayaranActivity.class);
                intent.putExtra("sewa_id",mSewaId);
                startActivity(intent);
            }
        });

        mButtonConfirmRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> confirmData = new HashMap<String, String>();

                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();


                confirmData.put("sewa_id",Integer.toString(mSewaId));
                if(mSession.checkUserType().equals("msc")) {
                    confirmData.put("user_id", Integer.toString(mMusicianId));
                }
                else if(mSession.checkUserType().equals("org")){
                    confirmData.put("user_id", Integer.toString(mOrganizerId));
                }
                buildUrl.serviceGighub.sendConfirmRequestData(confirmData).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Intent intent1 = new Intent(BookingDetailsActivity.this, MainActivity.class);
                        Toast.makeText(BookingDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("response ", response.code() +" "+ response.body().getMessage());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
            }
        });

    }
}
