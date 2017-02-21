package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.GroupBand;
import com.gighub.app.model.GrupBandResponse;
import com.gighub.app.model.MResponse;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupBandProfileActivity extends AppCompatActivity {

    private TextView mTextViewAnggota, mTextViewPosisi, mTextViewNamaBand, mTextViewKota, mTextViewHarga, mTextTipe, mTextViewBasis, mTextViewDeskripsi;
    private String mBasis, mAnggota, mPosisi, mNamaGrupBand, mKota, mHarga, mTipe, mDeskripsi, mPhoto,mCover, mYoutubeUrl, mWebsiteUrl, mUsernameSoundcloud, mUsernameReverbnation;
    private Button mButtonAddAnggota, mButtonRemoveAnggota,mButtonEditProfileGroupBand, mButtonSaveProfileGroup;
    private EditText mEditTextHargaBand,mEditTextBasisGroup, mEditTextNamaBand, mEditTextDescriptions,mEditTextKota,mEditTextYoutubeUrl, mEditTextWebsiteUrl, mEditTextUsernameSoundCloud, mEditTextReverbnation;
    private SessionManager mSession;
    private Context mContext;
    private int mGrupBandId, mAdminId;
    private ImageView mImageViewPhotoGroupBand, mImageViewCoverGroupBand;
    private LinearLayout mLinearLayoutEditGroupBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_band_profile);

        final Intent intent = getIntent();

        mContext = getApplicationContext();
        mSession = new SessionManager(mContext);

        mLinearLayoutEditGroupBand = (LinearLayout)findViewById(R.id.ll_edit_groupbandprofileactivity);

        mLinearLayoutEditGroupBand.setVisibility(View.GONE);
        mAnggota = intent.getStringExtra("anggota");
        mPosisi = intent.getStringExtra("posisi");
        mNamaGrupBand = intent.getStringExtra("nama_grupband");
        mHarga = Integer.toString(intent.getIntExtra("harga",0));
        mKota = intent.getStringExtra("kota");
        mTipe = intent.getStringExtra("tipe");
        mDeskripsi = intent.getStringExtra("deskripsi");
        mGrupBandId = intent.getIntExtra("grupband_id",0);
        mAdminId = intent.getIntExtra("admin_id",0);
        mPhoto = intent.getStringExtra("photo");
        mCover = intent.getStringExtra("cover");
        mYoutubeUrl = intent.getStringExtra("youtube_url");
        mWebsiteUrl = intent.getStringExtra("website_url");
        mUsernameSoundcloud = intent.getStringExtra("username_soundcloud");
        mUsernameReverbnation = intent.getStringExtra("username_reverbnation");
        mBasis = intent.getStringExtra("basis");

        mTextViewAnggota = (TextView)findViewById(R.id.tv_anggota_grupbandprofileactivity);
        mTextViewPosisi = (TextView)findViewById(R.id.tv_posisi_grupbandprofileactivity);
        mTextViewNamaBand = (TextView)findViewById(R.id.tv_nama_grupband_grupbandprofileactivity);
        mTextViewKota = (TextView)findViewById(R.id.tv_kota_grupband_grupbandprofileactivity);
        mTextViewHarga = (TextView)findViewById(R.id.tv_harga_grupband_grupbandprofileactivity);
        mTextTipe = (TextView)findViewById(R.id.tv_genre_grupband_grupbandprofileactivity);
        mTextViewDeskripsi = (TextView)findViewById(R.id.tv_deskripsi_grupbandprofileactivity);

        mEditTextNamaBand = (EditText)findViewById(R.id.et_nama_band_groupbandactivity);
        mEditTextDescriptions = (EditText)findViewById(R.id.et_descriptions_band_groupbandactivity);
        mEditTextBasisGroup = (EditText)findViewById(R.id.et_basis_group_groupbandactivity);
        mEditTextKota = (EditText)findViewById(R.id.et_kota_band_groupbandactivity);
        mEditTextYoutubeUrl = (EditText)findViewById(R.id.et_youtube_url_groupbandactivity);
        mEditTextWebsiteUrl = (EditText)findViewById(R.id.et_website_url_groupbandactivity);
        mEditTextUsernameSoundCloud = (EditText)findViewById(R.id.et_username_soundcloud_groupbandactivity);
        mEditTextReverbnation = (EditText)findViewById(R.id.et_username_reverbnation_groupbandactivity);
        mEditTextHargaBand = (EditText)findViewById(R.id.et_harga_band_groupbandactivity);

        mImageViewPhotoGroupBand = (ImageView)findViewById(R.id.img_photo_grupband_grupbandprofileactivity);
        mImageViewCoverGroupBand = (ImageView)findViewById(R.id.img_cover_band_grupband_grupbandprofileactivity);

        mButtonEditProfileGroupBand = (Button)findViewById(R.id.btn_edit_profile_group_groupbandactivity);
        mButtonSaveProfileGroup = (Button)findViewById(R.id.btn_save_profile_group_groupbandactivity);
        mButtonAddAnggota = (Button)findViewById(R.id.btn_add_anggota_groupbandprofileactivity);
        mButtonRemoveAnggota = (Button)findViewById(R.id.btn_remove_anggota_groupbandprofileactivity);

        mTextViewNamaBand.setText(mNamaGrupBand);
        mTextViewHarga.setText("Rp."+mHarga);
        mTextViewKota.setText(mKota);
        mTextTipe.setText(mTipe+" "+mBasis);
        mTextViewAnggota.setText("Anggota : "+mAnggota);
        mTextViewPosisi.setText("Posisi : "+mPosisi);
        mTextViewDeskripsi.setText(mDeskripsi);

        mButtonSaveProfileGroup.setVisibility(View.GONE);

        mButtonEditProfileGroupBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonSaveProfileGroup.setVisibility(View.VISIBLE);
                mLinearLayoutEditGroupBand.setVisibility(View.VISIBLE);
                mEditTextNamaBand.setText(mNamaGrupBand);
                mEditTextHargaBand.setText(mHarga);
                mEditTextDescriptions.setText(mDeskripsi);
                mEditTextKota.setText(mKota);
                mEditTextYoutubeUrl.setText(mYoutubeUrl);
                mEditTextWebsiteUrl.setText(mWebsiteUrl);
                mEditTextUsernameSoundCloud.setText(mUsernameSoundcloud);
                mEditTextReverbnation.setText(mUsernameReverbnation);
                mEditTextBasisGroup.setText(mBasis);
                mButtonEditProfileGroupBand.setVisibility(View.GONE);
            }
        });

        final Map<String, String> dataUpdateBand = new HashMap<>();
        mButtonSaveProfileGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();
                dataUpdateBand.put("id",Integer.toString(mGrupBandId));
                dataUpdateBand.put("nama_grupband", mEditTextNamaBand.getText().toString());
                dataUpdateBand.put("harga", mEditTextHargaBand.getText().toString());
                dataUpdateBand.put("deskripsi", mEditTextDescriptions.getText().toString());
                dataUpdateBand.put("kota", mEditTextKota.getText().toString());
                dataUpdateBand.put("youtube_video", mEditTextYoutubeUrl.getText().toString());
                dataUpdateBand.put("url_website", mEditTextWebsiteUrl.getText().toString());
                dataUpdateBand.put("username_soundcloud", mEditTextUsernameSoundCloud.getText().toString());
                dataUpdateBand.put("username_reverbnation", mEditTextReverbnation.getText().toString());
                dataUpdateBand.put("basis", mEditTextBasisGroup.getText().toString());
                buildUrl.serviceGighub.sendIdBandForUpdate(dataUpdateBand).enqueue(new Callback<GrupBandResponse>() {
                    @Override
                    public void onResponse(Call<GrupBandResponse> call, Response<GrupBandResponse> response) {
                        if(response.code()==200) {
                            Intent intent = new Intent(GroupBandProfileActivity.this, MainActivity.class);
                            Toast.makeText(GroupBandProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("response ", response.code() + " " + response.body().getMessage());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(GroupBandProfileActivity.this, "error ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GrupBandResponse> call, Throwable t) {

                    }
                });

            }
        });
        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        if(mPhoto!=null && !mPhoto.equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhoto)).into(mImageViewPhotoGroupBand);
        }
        if(mCover!=null && !mCover.equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mCover)).into(mImageViewCoverGroupBand);
        }

        if(mAdminId!=mSession.getMusicianDetails().getId()){
            mButtonAddAnggota.setVisibility(View.GONE);
            mButtonEditProfileGroupBand.setVisibility(View.GONE);
            mButtonSaveProfileGroup.setVisibility(View.GONE);
            mButtonRemoveAnggota.setVisibility(View.GONE);
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
