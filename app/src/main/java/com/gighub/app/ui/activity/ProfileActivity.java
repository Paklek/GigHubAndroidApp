package com.gighub.app.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.gighub.app.model.Bank;
import com.gighub.app.model.BankResponse;
import com.gighub.app.model.CloudinaryResponse;
import com.gighub.app.model.Genre;
import com.gighub.app.model.ResponseMusician;
import com.gighub.app.model.ResponseUser;
import com.gighub.app.model.Utility;
import com.gighub.app.ui.adapter.ListAddGenreAdapter;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.StaticFunction;
import com.gighub.app.util.StaticInt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQ_CODE_RESULT_PHOTO = 10001;
    private Toolbar toolbar;
    private ListView mListView;
    private GridView mGridView;
    private int mMusicianId, mOrganizerId, mGenreCount=0;
    private String mPilihanUser,mGenreDipilih="",mName, mFirstName, mLastName, mHarga, mEmail, mKota, mPhone, mDescriptions,  mYoutubeVideoURL, mWebsiteURL,mUsernameSounCloud, mUsernameReverbnation,mPhoto;
    private EditText mEditTextNoRek, mEditTextAtasNama,mEditTextNamaBank, mEditTextFirstName, mEditTextLastName,mEditTextHargaSewa,mEditTextEmail, mEditTextName, mEditTextKota, mEditTextPhoneNumber, mEditTextDescriptions, mEditTextYoutubeURL, mEditTextWebsiteURL, mEditTextUsernameSoundCloud, mEditTextUsernameReverbnation;
    private Button mButtonSaveInfoMusician,mButtonUploadPhoto, mButtonLoadBank;
    private View mViewEditTextFirstName, mViewEditTextLastname, mViewEditTextHargaSewa, mViewEditTextName, mViewEditTextKota, mViewEditTextPhoneNumber, mViewEditTextDescriptions, mViewEditTextYoutubeURL, mViewEditTextWebsiteURL, mViewEditTextUsernameSoundCloud, mViewEditTextUsernameReverbnation;
    private SessionManager mSession;
    private ImageView mImageViewPhoto;
    private CheckBox[] cbxs = new CheckBox[5];
    private List<Genre> mGenreList;
    private List<Genre> mMusicianGenres;
    private List<Bank> bank;
    private ListAddGenreAdapter listAddGenreAdapter;
    private TextView mTextViewMusicianGenres;
    private LinearLayout mLinearLayoutMusicianGenres, mLinearLayoutBank;
    private Context mContext;
    private File destination;
    private Map Result;
    private CloudinaryUrl cloudinaryUrl;
//    public static ProgressDialog progressDialog;
    CloudinaryResponse cloudinaryResponse;
//    Map <String, String> musicianGenreData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext = getApplicationContext();

//        progressDialog = new ProgressDialog(mContext);

        mGenreList = new ArrayList<Genre>();
        mMusicianGenres = new ArrayList<Genre>();
        bank = new ArrayList<Bank>();
        mSession = new SessionManager(getApplicationContext());
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        final Intent intent = getIntent();

        final Type type = new TypeToken<List<Genre>>(){}.getType();
//        bank = new Gson().fromJson(intent.getStringExtra("bank"),type);
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
//        mEditTextNoRek = (EditText)findViewById(R.id.et_no_rek_activityprofile);
//        mEditTextNamaBank = (EditText)findViewById(R.id.et_nama_bank_activityprofile);
//        mEditTextAtasNama = (EditText) findViewById(R.id.et_atas_nama_activityprofile);

        mTextViewMusicianGenres = (TextView)findViewById(R.id.tv_genres_activityprofile);

        mButtonSaveInfoMusician = (Button)findViewById(R.id.btn_saveinfo_activityprofile);
        mButtonUploadPhoto = (Button)findViewById(R.id.btn_upload_photo_activityprofile);
        mButtonLoadBank = (Button)findViewById(R.id.btn_load_bank_activityprofile);

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
        mLinearLayoutBank = (LinearLayout)findViewById(R.id.ll_bank_activityprofile);

        cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();

        mEditTextEmail.setEnabled(false);

        if(mSession.isLoggedIn()){
            if(mSession.checkUserType().equals("org")){
                mPhoto = mSession.getUserDetails().getPhoto();
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
//                mImageViewPhoto.setVisibility(View.GONE);
//                mButtonUploadPhoto.setVisibility(View.GONE);
                mLinearLayoutMusicianGenres.setVisibility(View.GONE);
                mTextViewMusicianGenres.setVisibility(View.GONE);
                mLinearLayoutBank.setVisibility(View.GONE);

                if(mPhoto!=null && !mPhoto.equals("")) {
                    Picasso.with(this).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhoto)).into(mImageViewPhoto);
                }


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

//                if(bank!=null){
//                    mEditTextNoRek.setText(bank.get(0).getNo_rek());
//                    mEditTextAtasNama.setText(bank.get(0).getAtas_nama());
//                    mEditTextNamaBank.setText(bank.get(0).getNama_bank());
//                }

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




        mButtonLoadBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();

                Map<String, String> dataBank =new HashMap<>();
                dataBank.put("id",Integer.toString(mMusicianId));
                buildUrl.serviceGighub.sendDataForBank(dataBank).enqueue(new Callback<BankResponse>() {
                    @Override
                    public void onResponse(Call<BankResponse> call, Response<BankResponse> response) {
                        if(response.code()==200) {
                            Intent _intent = new Intent(ProfileActivity.this, MusicianBankActivity.class);
                            _intent.putExtra("bank", new Gson().toJson(response.body().getBank()));
                            _intent.putExtra("musiciangenres",new Gson().toJson(mMusicianGenres));
                            _intent.putExtra("genres",new Gson().toJson(mGenreList));
                            startActivity(_intent);
                            Log.d("response", response.message());
                        }
                        else {
                            Log.d("response", "error :"+ response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BankResponse> call, Throwable t) {
                        Log.d("failed", t.getLocalizedMessage());
                    }
                });


            }
        });


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
                    mGridView.getChildAt(position).setBackground(mGridView.getResources().getDrawable(R.drawable.button_border_black));
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
                selectImage();
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
    Map<String, String> photoUpdate = new HashMap<>();

    private void doUpdatePhoto(){
        cloudinaryResponse = new Gson().fromJson(new Gson().toJson(responseCloudinary),CloudinaryResponse.class);

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        if (mSession.checkUserType().equals("msc")){
            mGenreDipilih = mGenreDipilih.substring(1,mGenreDipilih.length());
            photoUpdate.put("tipe_user","msc");
            photoUpdate.put("id", Integer.toString(mMusicianId));
            photoUpdate.put("photo", cloudinaryResponse.getPublic_id());

            buildUrl.serviceGighub.sendDataPhotoUpdate(photoUpdate).enqueue(new Callback<ResponseMusician>() {
                @Override
                public void onResponse(Call<ResponseMusician> call, Response<ResponseMusician> response) {
                    if (response.code() == 200) {
                        Toast.makeText(ProfileActivity.this, R.string.photo_profile_updated, Toast.LENGTH_SHORT).show();
                        mSession = new SessionManager(getApplicationContext());
                        mSession.createLoginSession(new Gson().toJson(response.body().getMusician()), "msc");

                    }
                    else {
                        Toast.makeText(ProfileActivity.this, R.string.failed_try_again, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseMusician> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, R.string.failed_try_again, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(mSession.checkUserType().equals("org")){
            photoUpdate.put("tipe_user","org");
            photoUpdate.put("id", Integer.toString(mOrganizerId));
            photoUpdate.put("photo", cloudinaryResponse.getPublic_id());

            buildUrl.serviceGighub.sendDataUserPhotoUpdate(photoUpdate).enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    if(response.code()==200) {
                        Toast.makeText(ProfileActivity.this, R.string.photo_profile_updated, Toast.LENGTH_SHORT).show();
                        mSession = new SessionManager(getApplicationContext());
                        mSession.createLoginSession(new Gson().toJson(response.body().getUser()), "org");
                    }
                    else {
                        Toast.makeText(ProfileActivity.this, R.string.failed_try_again, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, R.string.failed_try_again, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void doUpdateProfile(){

//        cloudinaryResponse = new Gson().fromJson(new Gson().toJson(responseCloudinary),CloudinaryResponse.class);

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
//      Start If For Check User for update
        if(mSession.checkUserType().equals("msc")) {
            mGenreDipilih = mGenreDipilih.substring(1,mGenreDipilih.length());
            dataUpdate.put("tipe_user","msc");
            dataUpdate.put("name", mEditTextName.getText().toString());
            dataUpdate.put("email", mEditTextEmail.getText().toString());
            dataUpdate.put("id", Integer.toString(mMusicianId));
            dataUpdate.put("deskripsi", mEditTextDescriptions.getText().toString());
            dataUpdate.put("no_telp", mEditTextPhoneNumber.getText().toString());
            dataUpdate.put("kota", mEditTextKota.getText().toString());
//            dataUpdate.put("photo", cloudinaryResponse.getPublic_id());
            dataUpdate.put("harga_sewa", mEditTextHargaSewa.getText().toString());
            dataUpdate.put("youtube_video", mEditTextYoutubeURL.getText().toString());
            dataUpdate.put("url_website", mEditTextWebsiteURL.getText().toString());
            dataUpdate.put("username_soundcloud", mEditTextUsernameSoundCloud.getText().toString());
            dataUpdate.put("username_reverbnation", mEditTextUsernameReverbnation.getText().toString());
            dataUpdate.put("genre_id",mGenreDipilih);
            dataUpdate.put("genre_count",Integer.toString(mGenreCount));
//            dataUpdate.put("no_rek",mEditTextNoRek.getText().toString());
//            dataUpdate.put("atas_nama", mEditTextAtasNama.getText().toString());
//            dataUpdate.put("nama_bank", mEditTextNamaBank.getText().toString());

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
                        Toast.makeText(ProfileActivity.this, "Failed to update, Please Complete Your Data", Toast.LENGTH_LONG).show();
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

    private void selectImage(){
        final CharSequence[] items = { "From Camera", "From Gallery",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Choose Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = Utility.checkPermission(mContext);
                if (items[which].equals("From Camera")){
                    mPilihanUser = "From Camera";
                    if(result){
                        cameraIntent();
                    }
                }
                else if(items[which].equals("From Gallery")){
                    mPilihanUser = "From Gallery";
                    if (result){
                        galleryIntent();
                    }
                }
                else if (items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, StaticInt.REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_RESULT_PHOTO);
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select File"),StaticInt.SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mPilihanUser.equals("From Camera"))
                        cameraIntent();
                    else if(mPilihanUser.equals("From Gallery"))
                        galleryIntent();
                } else {
//code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == StaticInt.SELECT_FILE) {
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == StaticInt.REQUEST_CAMERA){

                onCaptureImageResult(data);
            }else if(requestCode == REQ_CODE_RESULT_PHOTO){

                onSelectFromGalleryResult(data);

            }
        }
    }

    /**
     *
     */
    Uri uriGalery = null;
    Map<String,String> responseCloudinary;


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        uriGalery = data.getData();
        responseCloudinary = new HashMap<>();
//        ProgressDialog progressDialog = new ProgressDialog(mContext);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Uploading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        new uploadImageAsync(uriGalery).execute();
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mImageViewPhoto.setImageBitmap(bm);
//        progressDialog.dismiss();
    }
    public class uploadImageAsync extends AsyncTask{
        Uri uri;
//        ProgressDialog progressDialog;
        ProgressDialog progressDialog;
//        progressDialog.s(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Uploading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        public uploadImageAsync(Uri uri) {

            // nanti show loading disini (ProgressDialog)
//            StaticFunction.get(mContext).buildProgressDialog(mContext);
            progressDialog = new ProgressDialog(ProfileActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Uploading...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            Log.d("infoin","prepeare");


            this.uri = uri;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String url = StaticFunction.get(getApplicationContext()).getRealBaseURL(uri);
            try{
//                responseCloudinary = new Cloudinary().uploader().upload(url, ObjectUtils.emptyMap());
                CloudinaryUrl cloudinaryUrl =new CloudinaryUrl();
                cloudinaryUrl.buildCloudinaryUrl();
                responseCloudinary = cloudinaryUrl.cloudinary.uploader().upload(url, ObjectUtils.emptyMap());
            }catch(IOException e){

            }
            if(!responseCloudinary.isEmpty()){
                // nanti dismiss loading disini (ProgressDialog)
//                progressDialog.dismiss();
                Log.d("infoin","Sukses");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            updateTableUser();
//            Toast.makeText(ProfileActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
            doUpdatePhoto();
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            ProgressDialog progressDialog = new ProgressDialog(mContext);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage("Uploading...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
        }
    }

    private void updateTableUser() {

//        doUpdateProfile();

        //bawak si id ke retrofit
//        cloudinaryResponse.getPublic_id();

//        BuildUrl buildUrl = new BuildUrl();
//        buildUrl.buildBaseUrl();
//
////        dataUpdate.put("photo",cloudinaryResponse.getPublic_id());
//        buildUrl.serviceGighub.sendProfileUpdateDataMusician(dataUpdate).enqueue(new Callback<ResponseMusician>() {
//            @Override
//            public void onResponse(Call<ResponseMusician> call, Response<ResponseMusician> response) {
////                progressDialog.dismiss();
//                Toast.makeText(ProfileActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseMusician> call, Throwable t) {
//
//            }
//        });
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImageViewPhoto.setImageBitmap(thumbnail);

    }
    public class Upload extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String response = "";

            try {
                Log.d("uploading", "uploading");
                Result = cloudinaryUrl.cloudinary.uploader().upload(destination, ObjectUtils.emptyMap());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }
    }
}


