package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.ResponseMusician;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String mName, mFirstName, mLastName, mEmail, mKota, mPhone, mDescriptions,  mYoutubeVideoURL, mWebsiteURL,mUsernameSounCloud, mUsernameReverbnation;
    private EditText mEditTextFirstName, mEditTextLastName,mEditTextEmail, mEditTextName, mEditTextKota, mEditTextPhoneNumber, mEditTextDescriptions, mEditTextYoutubeURL, mEditTextWebsiteURL, mEditTextUsernameSoundCloud, mEditTextUsernameReverbnation;
    private Button mButtonSaveInfoMusician;
    private View mViewEditTextFirstName, mViewEditTextLastname, mViewEditTextName, mViewEditTextKota, mViewEditTextPhoneNumber, mViewEditTextDescriptions, mViewEditTextYoutubeURL, mViewEditTextWebsiteURL, mViewEditTextUsernameSoundCloud, mViewEditTextUsernameReverbnation;
    private SessionManager mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mSession = new SessionManager(getApplicationContext());
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        Intent intent = getIntent();

        mEditTextFirstName = (EditText)findViewById(R.id.et_first_name_activityprofile);
        mEditTextLastName = (EditText)findViewById(R.id.et_last_name_activityprofile);
        mEditTextEmail = (EditText)findViewById(R.id.et_email_activityprofile);
        mEditTextName = (EditText)findViewById(R.id.et_name_activityprofile);
        mEditTextKota = (EditText)findViewById(R.id.et_kota_activityprofile);
        mEditTextPhoneNumber = (EditText)findViewById(R.id.et_phone_number_activityprofile);
        mEditTextDescriptions = (EditText)findViewById(R.id.et_descriptions_activityprofile);
        mEditTextYoutubeURL = (EditText)findViewById(R.id.et_youtube_url_activityprofile);
        mEditTextWebsiteURL = (EditText)findViewById(R.id.et_website_url_activityprofile);
        mEditTextUsernameSoundCloud = (EditText)findViewById(R.id.et_username_soundcloud_activityprofile);
        mEditTextUsernameReverbnation = (EditText)findViewById(R.id.et_username_reverbnation_activityprofile);

        mButtonSaveInfoMusician = (Button)findViewById(R.id.btn_saveinfo_activityprofile);

        mViewEditTextFirstName = (View)findViewById(R.id.et_first_name_activityprofile);
        mViewEditTextLastname = (View)findViewById(R.id.et_last_name_activityprofile);
        mViewEditTextName = (View)findViewById(R.id.et_name_activityprofile);
        mViewEditTextKota = (View)findViewById(R.id.et_kota_activityprofile);
        mViewEditTextPhoneNumber = (View)findViewById(R.id.et_phone_number_activityprofile);
        mViewEditTextDescriptions = (View)findViewById(R.id.et_descriptions_activityprofile);
        mViewEditTextYoutubeURL = (View)findViewById(R.id.et_youtube_url_activityprofile);
        mViewEditTextWebsiteURL = (View)findViewById(R.id.et_website_url_activityprofile);
        mViewEditTextUsernameSoundCloud = (View)findViewById(R.id.et_username_soundcloud_activityprofile);
        mViewEditTextUsernameReverbnation = (View)findViewById(R.id.et_username_reverbnation_activityprofile);

        if(mSession.isLoggedIn()){
            if(mSession.checkUserType().equals("org")){
                mFirstName = mSession.getUserDetails().getFirst_name();
                mLastName = mSession.getUserDetails().getLast_name();
                mEmail = mSession.getUserDetails().getEmail();

                toolbar.setTitle("Profile - "+mFirstName);

                mEditTextName.setVisibility(mViewEditTextName.GONE);
                mEditTextPhoneNumber.setVisibility(mViewEditTextPhoneNumber.GONE);
                mEditTextDescriptions.setVisibility(mViewEditTextDescriptions.GONE);
                mEditTextKota.setVisibility(mViewEditTextKota.GONE);
                mEditTextYoutubeURL.setVisibility(mViewEditTextYoutubeURL.GONE);
                mEditTextWebsiteURL.setVisibility(mViewEditTextWebsiteURL.GONE);
                mEditTextUsernameSoundCloud.setVisibility(mViewEditTextUsernameSoundCloud.GONE);
                mEditTextUsernameReverbnation.setVisibility(mViewEditTextUsernameReverbnation.GONE);

                mEditTextFirstName.setText(mFirstName);
                mEditTextLastName.setText(mLastName);
                mEditTextEmail.setText(mEmail);
            }
            else if(mSession.checkUserType().equals("msc")){
                mName = mSession.getMusicianDetails().getName();
                mEmail = mSession.getMusicianDetails().getEmail();
                mKota = mSession.getMusicianDetails().getKota();
                mPhone = mSession.getMusicianDetails().getNo_telp();
                mDescriptions = mSession.getMusicianDetails().getDeskripsi();
                mYoutubeVideoURL = mSession.getMusicianDetails().getYoutube_video();
                mWebsiteURL = mSession.getMusicianDetails().getUrl_website();
                mUsernameSounCloud = mSession.getMusicianDetails().getUsername_soundcloud();
                mUsernameReverbnation = mSession.getMusicianDetails().getUsername_reverbnation();
                toolbar.setTitle("Profile - "+mName);

                mEditTextFirstName.setVisibility(mViewEditTextFirstName.GONE);
                mEditTextLastName.setVisibility(mViewEditTextLastname.GONE);

                mEditTextName.setText(mName);
                mEditTextEmail.setText(mEmail);
                mEditTextKota.setText(mKota);
                mEditTextPhoneNumber.setText(mPhone);
                mEditTextDescriptions.setText(mDescriptions);
                mEditTextYoutubeURL.setText(mYoutubeVideoURL);
                mEditTextWebsiteURL.setText(mWebsiteURL);
                mEditTextUsernameSoundCloud.setText(mUsernameSounCloud);
                mEditTextUsernameReverbnation.setText(mUsernameReverbnation);

            }

        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonSaveInfoMusician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdateProfile();
            }
        });
    }

    Map<String, String> dataUpdate = new HashMap<>();

    private void doUpdateProfile(){

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        dataUpdate.put("name",mEditTextName.getText().toString());

        buildUrl.serviceGighub.sendProfileUpdateDataMusician(dataUpdate).enqueue(new Callback<ResponseMusician>() {
            @Override
            public void onResponse(Call<ResponseMusician> call, Response<ResponseMusician> response) {
                if (response.code()==200){
                    Toast.makeText(ProfileActivity.this, "Berhasil update", Toast.LENGTH_LONG).show();
                    Log.d("Code",""+response.code());
                }
                else {
                    Log.d("Code", "Pesan Log : " + response.code());
                    Toast.makeText(ProfileActivity.this, "Gagal update", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMusician> call, Throwable t) {

            }
        });

    }
}
