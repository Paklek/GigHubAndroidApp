package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gighub.app.R;
import com.gighub.app.model.GroupBandsResponse;
import com.gighub.app.ui.adapter.MainViewPagerAdapter;
import com.gighub.app.ui.fragment.DiscoverGigFragment;
import com.gighub.app.ui.fragment.DiscoverMusicianFragment;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SessionManager mSession;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String mName, mFirstName, mLastName, mEmail,mPhone,mGenre;
    private int mMusicianId;

    public static final String FIRST_NAME = "fname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSession = new SessionManager(getApplicationContext());

        mSession.getSkipIntroStatus();

        if(mSession.isSkipIntroStatus()){

            mToolbar = (Toolbar)findViewById(R.id.toolbar);
            if(mSession.isLoggedIn()){
                if(mSession.checkUserType().equals("org")){
                    mName = mSession.getUserDetails().getFirst_name();
                }
                else if(mSession.checkUserType().equals("msc")){
                    mName = mSession.getMusicianDetails().getName();
//                    mSession.getMusicians().getGenreMusicians().get(0).getGenres().getGenre_name();
                    
//                    mMusicianId = mSession.getMusicianDetails().getId();
//                    mGenre = mSession.getMusicianDetails().getGenreMusician().get(0).getGenres().getGenre_name();
//                    mGenre = mSession.getMusicians().getGenreMusician().get(mSession.getGenreMusician().getMusician_id()).getGenres().getGenre_name();
//                    mName = mSession.getGenreMusician().getMusicians().getName();
//                    mGenre = mSession.getMusicians().getName();
//                    mGenre = mSession.getMusicians().getGenreMusician().get(mSession.getMusicians().getId()).getGenres().getGenre_name();
                }
                mToolbar.setTitle("GigHub - "+mName);
            }
            else {
                mToolbar.setTitle("Gig Hub - Discover");
            }
            setSupportActionBar(mToolbar);
            // my_child_toolbar is defined in the layout file

//            setSupportActionBar(toolbar);

            // Get a support ActionBar corresponding to this toolbar
//            ActionBar ab = getSupportActionBar();

            // Enable the Up button
//            ab.setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); -- Tombol Back

            mViewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(mViewPager);

            mTabLayout = (TabLayout) findViewById(R.id.tabs);
            mTabLayout.setupWithViewPager(mViewPager);


        }



    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DiscoverMusicianFragment(), "MUSICIANS");
        adapter.addFragment(new DiscoverGigFragment(), "GIGS");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        if(mSession.isLoggedIn()){
            if(mSession.checkUserType().equals("org")){
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);
            }
            else if(mSession.checkUserType().equals("msc")){
                menu.getItem(2).setVisible(true);
                menu.getItem(3).setVisible(true);
            }
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        if(!mSession.isLoggedIn()){
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_register:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(getApplicationContext(),RegisterAsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_login:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent1 = new Intent(getApplicationContext(),LoginAsActivity.class);



                startActivity(intent1);
                return true;

            case R.id.action_create_band:
////                // User chose the "Favorite" action, mark the current item
////                // as a favorite...
                Intent intent2 = new Intent(getApplicationContext(),CreateBandActivity.class);
                startActivity(intent2);
                return true;

            case R.id.action_bands:
                final Intent intent3 = new Intent(getApplicationContext(),GroupBandActivity.class);

//                intent3.putExtra("bands",new Gson().toJson())
                getBands(intent3);
//                startActivity(intent3);
                return true;

            case R.id.action_booking_list:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intent4 = new Intent(getApplicationContext(),BookingListActivity.class);
                startActivity(intent4);
                return true;

            case R.id.action_account:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intent5 = new Intent(getApplicationContext(),AccountActivity.class);

                if(mSession.isLoggedIn()){
                    if(mSession.checkUserType().equals("org")){
                        mFirstName = mSession.getUserDetails().getFirst_name();
                        mLastName = mSession.getUserDetails().getLast_name();
                        mEmail = mSession.getUserDetails().getEmail();
//                        mPhone = mSession

                        intent5.putExtra("first_name",mFirstName);
                        intent5.putExtra("last_name",mLastName);
                        intent5.putExtra("email",mEmail);
                    }
                    else if(mSession.checkUserType().equals("msc")){
                        mName = mSession.getMusicianDetails().getName();
                        mEmail = mSession.getMusicianDetails().getEmail();

                        intent5.putExtra("name",mName);
                        intent5.putExtra("email",mEmail);
                    }

                }
                startActivity(intent5);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void getBands(final Intent i){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        buildUrl.serviceGighub.getDataBand().enqueue(new Callback<GroupBandsResponse>() {
            @Override
            public void onResponse(Call<GroupBandsResponse> call, Response<GroupBandsResponse> response) {

                i.putExtra("bands",new Gson().toJson(response.body().getGroupBands()));
                Log.d("response",new Gson().toJson(response.body().getGroupBands()));
                Log.d("response",new Gson().toJson(response.body()));
                startActivity(i);
            }

            @Override
            public void onFailure(Call<GroupBandsResponse> call, Throwable t) {

            }
        });
    }



}
