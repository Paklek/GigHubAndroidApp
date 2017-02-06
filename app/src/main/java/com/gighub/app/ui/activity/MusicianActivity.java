package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.gighub.app.R;
import com.gighub.app.model.MusicianGenresResponse;
import com.gighub.app.model.PenyewaanResponse;
import com.gighub.app.model.ResponseCallGenre;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.model.YourBandResponse;
import com.gighub.app.model.YourGigResponse;
import com.gighub.app.ui.adapter.ListDiscoverGigAdapter;
import com.gighub.app.ui.adapter.MainViewPagerAdapter;
import com.gighub.app.ui.fragment.MusicianMusicFragment;
import com.gighub.app.ui.fragment.MusicianProfileFragment;
import com.gighub.app.ui.fragment.MusicianReviewFragment;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.StaticFunction;
import com.gighub.app.util.StaticString;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicianActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mImageViewProfile;
    private String mName, mPhoto, mTipeUser, mFirstName, mLastName, mEmail, mMusicianId, mOrganizerId;
    private Context mContext;
    private SessionManager mSession;
    private int pos = 0, mIdUser;
    private List<SearchResultModel> mSearchResultModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician);

        mSession = new SessionManager(getApplicationContext());

        if(mSession.isLoggedIn()) {

            if (mSession.checkUserType().equals("org")) {
                mName = mSession.getUserDetails().getFirst_name();
                mOrganizerId = Integer.toString(mSession.getUserDetails().getId());
            } else if (mSession.checkUserType().equals("msc")) {
                mName = mSession.getMusicianDetails().getName();
                mMusicianId = Integer.toString(mSession.getMusicianDetails().getId());
            }
        }

        mSearchResultModels = new ArrayList<SearchResultModel>();

        mImageViewProfile = (ImageView) findViewById(R.id.img_img_profile_musicianactivity);


        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        pos = intent.getIntExtra("posisi", pos);
        mPhoto = intent.getStringExtra("photo");
        final Type type = new TypeToken<List<SearchResultModel>>() {
        }.getType();
        mSearchResultModels = new Gson().fromJson(intent.getStringExtra("search"), type);
        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        if (mPhoto != null && !mPhoto.equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhoto)).into(mImageViewProfile);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        mToolbar.setTitle("" + mName);
        setSupportActionBar(mToolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); -- Tombol Back

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MusicianProfileFragment(), "PROFILE");
//        adapter.addFragment(new MusicianMusicFragment(), "MUSICS");
        adapter.addFragment(new MusicianReviewFragment(), "REVIEWS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

//        if(!mSession.isLoggedIn()){
//            menu.getItem(2).setVisible(false);
//            menu.getItem(3).setVisible(false);
//            menu.getItem(4).setVisible(false);
//            menu.getItem(5).setVisible(false);
//            menu.getItem(6).setVisible(false);
//        }

//        StaticFunction staticFunction = new StaticFunction();
//        staticFunction.createOptionMenu(menu, mSession, MusicianActivity.class);

        if(mSession.isLoggedIn()){
            if(mSession.checkUserType().equals("org")){
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);
                menu.getItem(6).setVisible(false);
            }
            else if(mSession.checkUserType().equals("msc")){
                menu.getItem(2).setVisible(true);
                menu.getItem(3).setVisible(true);
                menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(false);

            }
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        if(!mSession.isLoggedIn()){
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
            menu.getItem(4).setVisible(false);
            menu.getItem(5).setVisible(false);
            menu.getItem(6).setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_register:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(getApplicationContext(), RegisterAsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_login:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent1 = new Intent(getApplicationContext(), LoginAsActivity.class);


                startActivity(intent1);
                return true;

            case R.id.action_create_band:
////                // User chose the "Favorite" action, mark the current item
////                // as a favorite...

                getGenreForCreateBand();
//
//                BuildUrl buildUrl = new BuildUrl();
//                buildUrl.buildBaseUrl();
//
//                buildUrl.serviceGighub.loadMusicianGenre().enqueue(new Callback<ResponseCallGenre>() {
//                    @Override
//                    public void onResponse(Call<ResponseCallGenre> call, Response<ResponseCallGenre> response) {
//                        if(response.code()==200){
//                            Intent intent2 = new Intent(getApplicationContext(),CreateBandActivity.class);
//                            intent2.putExtra("genres",new Gson().toJson(response.body().getGenreList()));
//                            startActivity(intent2);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseCallGenre> call, Throwable t) {
//
//                    }
//                });


                return true;

            case R.id.action_bands:
                final Intent intent3 = new Intent(getApplicationContext(), GroupBandActivity.class);

//                intent3.putExtra("bands",new Gson().toJson())
                getBands(intent3);
//                startActivity(intent3);
                return true;

            case R.id.action_create_gig:
                Intent intent4 = new Intent(getApplicationContext(), CreateGigActivity.class);
                startActivity(intent4);
                return true;

            case R.id.action_yourgigs:
                Intent intent5 = new Intent(getApplicationContext(), YourGigActivity.class);
                getYourGigs(intent5);
                return true;

            case R.id.action_booking_list:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intent6 = new Intent(getApplicationContext(), BookingListActivity.class);

                if (mSession.isLoggedIn()) {
                    if (mSession.checkUserType().equals("org")) {
                        mIdUser = mSession.getUserDetails().getId();
                        mTipeUser = "org";
                    } else if (mSession.checkUserType().equals("msc")) {
                        mIdUser = mSession.getMusicianDetails().getId();
                        mTipeUser = "msc";
                    }

                }
//                BuatProgressDialog();
                StaticFunction staticFunction = new StaticFunction();
                staticFunction.buildProgressDialog(this);
//
                Book(intent6, mIdUser, mTipeUser);
//                onProccess(intent6,mIdUser,mTipeUser);
//                startActivity(intent6);

                return true;

            case R.id.action_account:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                final Intent intent7 = new Intent(getApplicationContext(), AccountActivity.class);

                if (mSession.isLoggedIn()) {
                    if (mSession.checkUserType().equals("org")) {
                        mFirstName = mSession.getUserDetails().getFirst_name();
                        mLastName = mSession.getUserDetails().getLast_name();
                        mEmail = mSession.getUserDetails().getEmail();
//                        mPhone = mSession

                        intent7.putExtra("first_name", mFirstName);
                        intent7.putExtra("last_name", mLastName);
                        intent7.putExtra("email", mEmail);
                    } else if (mSession.checkUserType().equals("msc")) {
                        mName = mSession.getMusicianDetails().getName();
                        mEmail = mSession.getMusicianDetails().getEmail();

                        intent7.putExtra("name", mName);
                        intent7.putExtra("email", mEmail);
                    }

                }

                if (mSession.checkUserType().equals("msc")) {

                    BuildUrl buildUrl = new BuildUrl();
                    buildUrl.buildBaseUrl();

                    musicianGenreData.put("user_id", Integer.toString(mSession.getMusicianDetails().getId()));
                    buildUrl.serviceGighub.sendGenreMusicianData(musicianGenreData).enqueue(new Callback<MusicianGenresResponse>() {
                        @Override
                        public void onResponse(Call<MusicianGenresResponse> call, Response<MusicianGenresResponse> response) {
                            if (response.code() == 200) {
                                intent7.putExtra("musiciangenres", new Gson().toJson(response.body().getMusicianGenres()));
                                startActivity(intent7);
                            } else {
                                Toast.makeText(MusicianActivity.this, "" + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MusicianGenresResponse> call, Throwable t) {

                        }
                    });
                } else {
                    startActivity(intent7);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    Map<String, String> musicianGenreData = new HashMap<>();
    Map<String, String> sendYourBandData = new HashMap<>();

    private void getBands(final Intent i) {
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        sendYourBandData.put("user_id", mMusicianId);

        buildUrl.serviceGighub.sendYourBands(sendYourBandData).enqueue(new Callback<YourBandResponse>() {
            @Override
            public void onResponse(Call<YourBandResponse> call, Response<YourBandResponse> response) {
                i.putExtra("bands", new Gson().toJson(response.body().getGroupBands()));
                Log.d("response", new Gson().toJson(response.body().getGroupBands()));
                Log.d("response", new Gson().toJson(response.body()));
                startActivity(i);
            }

            @Override
            public void onFailure(Call<YourBandResponse> call, Throwable t) {

            }
        });
    }


    Map<String, String> organizerId = new HashMap<>();

    private void getYourGigs(final Intent i) {
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        Log.d("response", mOrganizerId);
        Log.d("response", mSession.getUserDetails().getFirst_name());

        organizerId.put("user_id", mOrganizerId);

        buildUrl.serviceGighub.loadYourGig(organizerId).enqueue(new Callback<YourGigResponse>() {
            @Override
            public void onResponse(Call<YourGigResponse> call, Response<YourGigResponse> response) {
                i.putExtra("yourgigs", new Gson().toJson(response.body().getYourgigs()));
                Log.d("response", new Gson().toJson(response.body().getYourgigs()));
                Log.d("response", new Gson().toJson(response.body()));
                startActivity(i);
            }

            @Override
            public void onFailure(Call<YourGigResponse> call, Throwable t) {
                Log.d("fail", t.getCause().getMessage());
                Log.d("fail", t.getMessage());
                Log.d("fail", t.getCause().getLocalizedMessage());
            }
        });
    }

    Map<String, String> idUser = new HashMap<>();
    Map<String, String> forOnProccess = new HashMap<>();

    private void Book(final Intent i, int userId, String tipeUser) {
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        idUser.put("tipe_user", tipeUser);
        idUser.put("user_id", Integer.toString(userId));
        buildUrl.serviceGighub.sendIdUserForBook(idUser).enqueue(new Callback<PenyewaanResponse>() {
            @Override
            public void onResponse(Call<PenyewaanResponse> call, Response<PenyewaanResponse> response) {
                if (response.code() == 200) {
                    i.putExtra("onreq", new Gson().toJson(response.body().getPenyewaanList()));
                    i.putExtra("onproc", new Gson().toJson(response.body().getPenyewaanList()));
                    startActivity(i);
                } else {
                    Log.d("response", response.message());
                    Toast.makeText(getApplicationContext(), "Booking list is empty", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PenyewaanResponse> call, Throwable t) {
                Log.d("fail", t.getCause().getMessage());
            }
        });

    }

    private void getGenreForCreateBand(){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        buildUrl.serviceGighub.loadMusicianGenre().enqueue(new Callback<ResponseCallGenre>() {
            @Override
            public void onResponse(Call<ResponseCallGenre> call, Response<ResponseCallGenre> response) {
                if(response.code()==200){
                    Intent intent2 = new Intent(getApplicationContext(),CreateBandActivity.class);
                    intent2.putExtra("genres",new Gson().toJson(response.body().getGenreList()));
                    startActivity(intent2);
                }
            }

            @Override
            public void onFailure(Call<ResponseCallGenre> call, Throwable t) {

            }
        });
    }
}
