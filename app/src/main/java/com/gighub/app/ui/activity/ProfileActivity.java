package com.gighub.app.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.ResponseCallGenre;
import com.gighub.app.model.ResponseMusician;
import com.gighub.app.model.ResponseUser;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.adapter.ListAddGenreAdapter;
import com.gighub.app.ui.adapter.ListSearchResultAdapter;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView mListView;
    private GridView mGridView;
    private int mMusicianId, mOrganizerId, mGenreCount=0;
    private String mGenreDipilih="",mName, mFirstName, mLastName, mHarga, mEmail, mKota, mPhone, mDescriptions,  mYoutubeVideoURL, mWebsiteURL,mUsernameSounCloud, mUsernameReverbnation,mPhoto;
    private EditText mEditTextFirstName, mEditTextLastName,mEditTextHargaSewa,mEditTextEmail, mEditTextName, mEditTextKota, mEditTextPhoneNumber, mEditTextDescriptions, mEditTextYoutubeURL, mEditTextWebsiteURL, mEditTextUsernameSoundCloud, mEditTextUsernameReverbnation;
    private Button mButtonSaveInfoMusician,mButtonUploadPhoto;
    private View mViewEditTextFirstName, mViewEditTextLastname, mViewEditTextHargaSewa, mViewEditTextName, mViewEditTextKota, mViewEditTextPhoneNumber, mViewEditTextDescriptions, mViewEditTextYoutubeURL, mViewEditTextWebsiteURL, mViewEditTextUsernameSoundCloud, mViewEditTextUsernameReverbnation;
    private SessionManager mSession;
    private ImageView mImageViewPhoto;
    private CheckBox[] cbxs = new CheckBox[5];
    private List<Genre> mGenreList;
    private List<Genre> mMusicianGenres;
    private ListAddGenreAdapter listAddGenreAdapter;
    private TextView mTextViewMusicianGenres;
    private LinearLayout mLinearLayoutMusicianGenres;
//    Map <String, String> musicianGenreData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mGenreList = new ArrayList<Genre>();
        mMusicianGenres = new ArrayList<Genre>();

        mSession = new SessionManager(getApplicationContext());
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        Intent intent = getIntent();

        final Type type = new TypeToken<List<Genre>>(){}.getType();
        mMusicianGenres = new Gson().fromJson(intent.getStringExtra("musiciangenres"),type);
        mGenreList = new Gson().fromJson(intent.getStringExtra("genres"),type);


        mEditTextFirstName = (EditText)findViewById(R.id.et_first_name_activityprofile);
        mEditTextLastName = (EditText)findViewById(R.id.et_last_name_activityprofile);
        mEditTextHargaSewa = (EditText)findViewById(R.id.et_harga_sewa_activityprofile);
        mEditTextEmail = (EditText)findViewById(R.id.et_email_activityprofile);
        mEditTextName = (EditText)findViewById(R.id.et_name_activityprofile);
        mEditTextKota = (EditText)findViewById(R.id.et_kota_activityprofile);
        mEditTextPhoneNumber = (EditText)findViewById(R.id.et_phone_number_activityprofile);
        mEditTextDescriptions = (EditText)findViewById(R.id.et_descriptions_activityprofile);
        mEditTextYoutubeURL = (EditText)findViewById(R.id.et_youtube_url_activityprofile);
        mEditTextWebsiteURL = (EditText)findViewById(R.id.et_website_url_activityprofile);
        mEditTextUsernameSoundCloud = (EditText)findViewById(R.id.et_username_soundcloud_activityprofile);
        mEditTextUsernameReverbnation = (EditText)findViewById(R.id.et_username_reverbnation_activityprofile);
        mImageViewPhoto = (ImageView)findViewById(R.id.img_photo_activityprofile);

        mTextViewMusicianGenres = (TextView)findViewById(R.id.tv_genres_activityprofile);

        mButtonSaveInfoMusician = (Button)findViewById(R.id.btn_saveinfo_activityprofile);
        mButtonUploadPhoto = (Button)findViewById(R.id.btn_upload_photo_activityprofile);

        mViewEditTextFirstName = (View)findViewById(R.id.et_first_name_activityprofile);
        mViewEditTextLastname = (View)findViewById(R.id.et_last_name_activityprofile);
        mViewEditTextHargaSewa = (View)findViewById(R.id.et_harga_sewa_activityprofile);
        mViewEditTextName = (View)findViewById(R.id.et_name_activityprofile);
        mViewEditTextKota = (View)findViewById(R.id.et_kota_activityprofile);
        mViewEditTextPhoneNumber = (View)findViewById(R.id.et_phone_number_activityprofile);
        mViewEditTextDescriptions = (View)findViewById(R.id.et_descriptions_activityprofile);
        mViewEditTextYoutubeURL = (View)findViewById(R.id.et_youtube_url_activityprofile);
        mViewEditTextWebsiteURL = (View)findViewById(R.id.et_website_url_activityprofile);
        mViewEditTextUsernameSoundCloud = (View)findViewById(R.id.et_username_soundcloud_activityprofile);
        mViewEditTextUsernameReverbnation = (View)findViewById(R.id.et_username_reverbnation_activityprofile);
        mGridView = (GridView) findViewById(R.id.lv_genres_activityprofile);
        mLinearLayoutMusicianGenres = (LinearLayout)findViewById(R.id.ll_genre_musician_activityprofile);

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();

        mEditTextEmail.setEnabled(false);

        if(mSession.isLoggedIn()){
            if(mSession.checkUserType().equals("org")){
                mOrganizerId = mSession.getUserDetails().getId();
                mFirstName = mSession.getUserDetails().getFirst_name();
                mLastName = mSession.getUserDetails().getLast_name();
                mEmail = mSession.getUserDetails().getEmail();

                toolbar.setTitle("Profile - "+mFirstName);

                mEditTextName.setVisibility(mViewEditTextName.GONE);
                mEditTextHargaSewa.setVisibility(mViewEditTextHargaSewa.GONE);
                mEditTextPhoneNumber.setVisibility(mViewEditTextPhoneNumber.GONE);
                mEditTextDescriptions.setVisibility(mViewEditTextDescriptions.GONE);
                mEditTextKota.setVisibility(mViewEditTextKota.GONE);
                mEditTextYoutubeURL.setVisibility(mViewEditTextYoutubeURL.GONE);
                mEditTextWebsiteURL.setVisibility(mViewEditTextWebsiteURL.GONE);
                mEditTextUsernameSoundCloud.setVisibility(mViewEditTextUsernameSoundCloud.GONE);
                mEditTextUsernameReverbnation.setVisibility(mViewEditTextUsernameReverbnation.GONE);
                mImageViewPhoto.setVisibility(View.GONE);
                mButtonUploadPhoto.setVisibility(View.GONE);
                mLinearLayoutMusicianGenres.setVisibility(View.GONE);
                mTextViewMusicianGenres.setVisibility(View.GONE);


                mEditTextFirstName.setText(mFirstName);
                mEditTextLastName.setText(mLastName);
                mEditTextEmail.setText(mEmail);
            }
            else if(mSession.checkUserType().equals("msc")){
                mMusicianId = mSession.getMusicianDetails().getId();
                mPhoto = mSession.getMusicianDetails().getPhoto();
                mName = mSession.getMusicianDetails().getName();
                mEmail = mSession.getMusicianDetails().getEmail();
                mHarga = mSession.getMusicianDetails().getHarga_sewa();
                mKota = mSession.getMusicianDetails().getKota();
                mPhone = mSession.getMusicianDetails().getNo_telp();
                mDescriptions = mSession.getMusicianDetails().getDeskripsi();
                mYoutubeVideoURL = mSession.getMusicianDetails().getYoutube_video();
                mWebsiteURL = mSession.getMusicianDetails().getUrl_website();
                mUsernameSounCloud = mSession.getMusicianDetails().getUsername_soundcloud();
                mUsernameReverbnation = mSession.getMusicianDetails().getUsername_reverbnation();
                toolbar.setTitle("Profile - "+mName);

                mEditTextFirstName.setVisibility(View.GONE);
                mEditTextLastName.setVisibility(View.GONE);



                if(mPhoto!=null && !mPhoto.equals("")) {
                    Picasso.with(this).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhoto)).into(mImageViewPhoto);
                }
                mEditTextName.setText(mName);
                mEditTextEmail.setText(mEmail);
                mEditTextHargaSewa.setText(mHarga);
                mEditTextKota.setText(mKota);
                mEditTextPhoneNumber.setText(mPhone);
                mEditTextDescriptions.setText(mDescriptions);
                mEditTextYoutubeURL.setText(mYoutubeVideoURL);
                mEditTextWebsiteURL.setText(mWebsiteURL);
                mEditTextUsernameSoundCloud.setText(mUsernameSounCloud);
                mEditTextUsernameReverbnation.setText(mUsernameReverbnation);

                String tmpGenres="";
                for (int j = 0;j<mMusicianGenres.size();j++){
                    tmpGenres += mMusicianGenres.get(j).getGenre_name()+" ";
                    mGenreDipilih += " "+mMusicianGenres.get(j).getId();
                    mGenreCount+=1;
                }
                mTextViewMusicianGenres.setText("Genres : "+tmpGenres);


            }

        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        final Type type = new TypeToken<List<Genre>>(){}.getType();

        listAddGenreAdapter = new ListAddGenreAdapter(getApplicationContext(),mGenreList,mMusicianGenres);
//        mListView = (ListView)findViewById(R.id.lv_genres_activityprofile);
//        for (int g = 0; g<mGenreList.size();g++){


//        mGridView.getChildAt(0).setBackground(mGridView.getResources().getDrawable(R.drawable.button_border2));

//        mGridView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });
        final boolean[] selected = new boolean[mGenreList.size()];





        mGridView.setAdapter(listAddGenreAdapter);

//        for (int g=0;g<mGenreList.size();g++){
//            for (int e = 0;e<mMusicianGenres.size();e++){
//                if (mMusicianGenres.get(e).getGenre_name().equals(mGenreList.get(g).getGenre_name())){
//                    mGridView.getChildAt(g).setBackgroundColor(Color.BLACK);
////                    mGridView.getChildAt(g).setEnabled();
//                    mGenreList.get(g).setSelected(true);
////                mGr
//                }
//                else
//                {
//                    mGridView.getChildAt(g).setBackgroundColor(Color.BLACK);
//                    mGenreList.get(g).setSelected(false);
//                }
//            }
//        }


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ProfileActivity.this,mGenreList.get(position).getGenre_name(),Toast.LENGTH_SHORT).show();
                Log.d("onItemClick",""+mGenreList.get(position).getGenre_name());
//                mGridView.getChildAt(position);
//                Log.d("isSelected",""+parent.getChildAt(position).isSelected());
//                Log.d("isPressed",""+parent.getChildAt(position).isPressed());
//                Log.d("isSelected",""+parent.getChildAt(position).is);
                Log.d("isSelected",""+mGridView.getChildAt(position).isSelected());
                Log.d("isPressed",""+mGridView.getChildAt(position).isPressed());
                Log.d("isSelected",""+mGridView.isItemChecked(position));

//                Log.d("isSelected",""+selected[position]);
                if (!mGenreList.get(position).getSelected()){
                    mGridView.getChildAt(position).setBackground(mGridView.getResources().getDrawable(R.drawable.button_border2));
                    mGenreList.get(position).setSelected(true);
                    mGenreDipilih += " "+mGenreList.get(position).getId();
                    mGenreCount +=1;
                }
                else
                {
                    mGridView.getChildAt(position).setBackground(mGridView.getResources().getDrawable(R.drawable.spinner_drawable));
                    mGenreList.get(position).setSelected(false);
                    mGenreDipilih = mGenreDipilih.replace(" "+mGenreList.get(position).getId(),"");
                    mGenreCount -= 1;

                }

            }
        });


//        for(int g =0;g<mGenreList.size();g++){
//            for(int e=0;e<mMusicianGenres.size();e++){
//                if (mMusicianGenres.get(e).getGenre_name().equals(mGenreList.get(g).getGenre_name())){
//                    mGridView.getChildAt(g).setBackgroundDrawable(mGridView.getResources().getDrawable(R.drawable.button_border2));
//                    mGenreList.get(g).setSelected(true);
//                }
//
//            }
////            if (mGenreList.get(g).getSelected()){
////                mGridView.getChildAt(g).setBackgroundDrawable(mGridView.getChildAt(g).getResources().getDrawable(R.drawable.button_border2));
////
////            }
//        }




        mButtonUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        mButtonSaveInfoMusician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdateProfile();
            }
        });
    }

    Map<String, String> dataUpdate = new HashMap<>();

    private void doUpdateProfile(){

        mGenreDipilih = mGenreDipilih.substring(1,mGenreDipilih.length());

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
//      Start If For Check User for update
        if(mSession.checkUserType().equals("msc")) {
            dataUpdate.put("tipe_user","msc");
            dataUpdate.put("name", mEditTextName.getText().toString());
            dataUpdate.put("email", mEditTextEmail.getText().toString());
            dataUpdate.put("id", Integer.toString(mMusicianId));
            dataUpdate.put("deskripsi", mEditTextDescriptions.getText().toString());
            dataUpdate.put("no_telp", mEditTextPhoneNumber.getText().toString());
            dataUpdate.put("kota", mEditTextKota.getText().toString());
            dataUpdate.put("harga_sewa", mEditTextHargaSewa.getText().toString());
            dataUpdate.put("youtube_video", mEditTextYoutubeURL.getText().toString());
            dataUpdate.put("url_website", mEditTextWebsiteURL.getText().toString());
            dataUpdate.put("username_soundcloud", mEditTextUsernameSoundCloud.getText().toString());
            dataUpdate.put("username_reverbnation", mEditTextUsernameReverbnation.getText().toString());
            dataUpdate.put("genre_id",mGenreDipilih);
            dataUpdate.put("genre_count",Integer.toString(mGenreCount));
            Log.d("mGenreCount",Integer.toString(mGenreCount));
            Log.d("mGenreDipilih",mGenreDipilih);
            buildUrl.serviceGighub.sendProfileUpdateDataMusician(dataUpdate).enqueue(new Callback<ResponseMusician>() {
                @Override
                public void onResponse(Call<ResponseMusician> call, Response<ResponseMusician> response) {

                    if (response.code() == 200) {
                        mSession = new SessionManager(getApplicationContext());
                        mSession.createLoginSession(new Gson().toJson(response.body().getMusician()), "msc");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("Code", "" + response.code());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Log.d("Code", "Pesan Log : " + response.code());
                        Toast.makeText(ProfileActivity.this, "Gagal update", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseMusician> call, Throwable t) {
                    Log.d("fail",t.getCause().getMessage());
                }

            });
        }
        else if(mSession.checkUserType().equals("org")) {
            dataUpdate.put("id",Integer.toString(mOrganizerId));
            dataUpdate.put("tipe_user","org");
            dataUpdate.put("first_name",mEditTextFirstName.getText().toString());
            dataUpdate.put("last_name",mEditTextLastName.getText().toString());
            dataUpdate.put("email",mEditTextEmail.getText().toString());

            buildUrl.serviceGighub.sendProfileUpdateDataOrganizer(dataUpdate).enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    if (response.code() == 200) {
                        mSession = new SessionManager(getApplicationContext());
                        mSession.createLoginSession(new Gson().toJson(response.body().getUser()), "org");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("Code", "" + response.code());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Log.d("Code", "Pesan Log : " + response.code());
                        Toast.makeText(ProfileActivity.this, "Gagal update", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {
                    Log.d("fail",t.getCause().getMessage());
                }
            });

        }
//      End If for user Update
    }
}
