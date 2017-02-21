package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Response;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.model.YourReview;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ReviewMusicianActivity extends AppCompatActivity {

    private RatingBar mRatingBar;
    private float mRating;
    private TextView mTextViewHowAboutTheShow ,mTextViewMusicianName,mTextViewRatingNumber, mTextViewMessageReview, mTextViewNamaOrganizer, mTextViewTanggalReview;
    private EditText mEditTextReview;
    private Button mButtonSendReview;
    private ImageView mImageViewMusicianPhoto, mImageViewUserReviewerPhoto;
    private String mPhotoMusisi,mPhotoUser,mMusicianName,mStatus, mStatusRequest,mNamaUser;
    private int mSewaId,mOrganizerId;
    private Context mContext;
    private SessionManager mSession;
    private LinearLayout mLinearLayoutReviewMessage;
    private List<YourReview> mYourReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_musician);
        mContext = getApplicationContext();
        Intent intent = getIntent();
        mYourReview = new ArrayList<YourReview>();
        final Type type = new TypeToken<List<YourReview>>() {}.getType();
        mYourReview = new Gson().fromJson(intent.getStringExtra("yourreview"),type);

        if(mYourReview.size()>0){
            mPhotoUser = mYourReview.get(0).getPhoto();
        }
        else {
            mPhotoUser = "";
        }
        mSession = new SessionManager(getApplicationContext());
        mRatingBar = (RatingBar)findViewById(R.id.ratbar_reviewmusician);
        mTextViewMusicianName = (TextView)findViewById(R.id.tv_musician_name_reviewmusician);
        mTextViewRatingNumber = (TextView)findViewById(R.id.tv_rating_number_reviewmusician);
        mEditTextReview = (EditText)findViewById(R.id.et_review_musician__reviewmusician);
        mButtonSendReview = (Button)findViewById(R.id.btn_send_review_reviewmusician);
        mImageViewMusicianPhoto = (ImageView)findViewById(R.id.img_photo_musician_reviewmusician);
        mImageViewUserReviewerPhoto = (ImageView)findViewById(R.id.img_user_reviewer_reviewmusician);
        mLinearLayoutReviewMessage = (LinearLayout)findViewById(R.id.ll_review_message_reviewmusician);
        mTextViewMessageReview = (TextView)findViewById(R.id.tv_pesan_review_reviewmusician);
        mTextViewNamaOrganizer = (TextView)findViewById(R.id.tv_nama_reviewer_reviewmusician);
        mTextViewTanggalReview = (TextView)findViewById(R.id.tv_tanggal_review_reviewmusician);
        mTextViewHowAboutTheShow = (TextView)findViewById(R.id.tv_how_about_the_show);

//        Intent intent = getIntent();
        mPhotoMusisi = intent.getStringExtra("photo");
        mMusicianName = intent.getStringExtra("nama_musisi");
        mSewaId = intent.getIntExtra("sewa_id",0);
        mOrganizerId = mSession.getUserDetails().getId();
        mStatus = intent.getStringExtra("status");
        mStatusRequest = intent.getStringExtra("status_request");
        mNamaUser = intent.getStringExtra("nama_user");

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        if (mStatus.equals("4")&&mStatusRequest.equals("1")){
            mEditTextReview.setVisibility(View.GONE);
            mButtonSendReview.setVisibility(View.GONE);
            mTextViewRatingNumber.setVisibility(View.GONE);

            if(mYourReview.size()>0){
//                mTextViewHowAboutTheShow.setVisibility(View.GONE);
                mTextViewMessageReview.setText(mYourReview.get(0).getPesan());
                mRatingBar.setRating(Float.parseFloat(mYourReview.get(0).getNilai()+".0"));
                mRatingBar.setIsIndicator(true);
                mTextViewNamaOrganizer.setText(mNamaUser);
                mTextViewTanggalReview.setText(mYourReview.get(0).getCreated_at());
            }
            else {
                mTextViewHowAboutTheShow.setText("No Review");
                mTextViewMessageReview.setVisibility(View.GONE);
//                mRatingBar.setRating(Float.parseFloat(mYourReview.getNilai()+".0"));
                mRatingBar.setVisibility(View.GONE);
                mTextViewNamaOrganizer.setVisibility(View.GONE);
                mTextViewTanggalReview.setVisibility(View.GONE);
                mLinearLayoutReviewMessage.setVisibility(View.GONE);
            }
//            mTextViewMessageReview.setText(mYourReview.getPesan());
//            mRatingBar.setRating(Float.parseFloat(mYourReview.getNilai()+".0"));
//            mRatingBar.setIsIndicator(true);
//            mTextViewNamaOrganizer.setText(mYourReview.getFirst_name()+" "+mYourReview.getLast_name());
//            mTextViewTanggalReview.setText(mYourReview.getCreated_at());
//            mRatingBar.setBackgroundColor();
            if (mPhotoUser != null && !mPhotoUser.equals("")) {
                Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhotoUser)).into(mImageViewUserReviewerPhoto);
            }
        }
        else {
            mLinearLayoutReviewMessage.setVisibility(View.GONE);
        }

        if (mPhotoMusisi != null && !mPhotoMusisi.equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhotoMusisi)).into(mImageViewMusicianPhoto);
        }

        mTextViewMusicianName.setText(mMusicianName);

        mRatingBar.setStepSize(Float.parseFloat("1"));

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mRating = mRatingBar.getRating();
                mRatingBar.setRating(mRating);
                mTextViewRatingNumber.setText(""+mRating);
            }
        });

        mButtonSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent1 = new Intent(ReviewMusicianActivity.this,MainActivity.class);
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();
                Map<String,String> dataReview = new HashMap<String, String>();

                dataReview.put("sewa_id",Integer.toString(mSewaId));
                dataReview.put("user_id",Integer.toString(mOrganizerId));
                dataReview.put("nilai",mTextViewRatingNumber.getText().toString().substring(0,1));
                dataReview.put("pesan",mEditTextReview.getText().toString());
                buildUrl.serviceGighub.sendDataReview(dataReview).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if(response.code()==200) {
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Toast.makeText(ReviewMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(intent1);
                        }
                        if(response.code()==500){
                            Log.d("error", response.message()+" "+response.code());
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
