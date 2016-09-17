package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gighub.app.R;
import com.gighub.app.ui.adapter.MainViewPagerAdapter;
import com.gighub.app.ui.fragment.DiscoverGigFragment;
import com.gighub.app.ui.fragment.DiscoverMusicianFragment;
import com.gighub.app.util.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager mSession;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String mName;

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
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
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

//            case R.id.action_create_musician:
////                // User chose the "Favorite" action, mark the current item
////                // as a favorite...
//                Intent intent2 = new Intent(getApplicationContext(),CreateMusicianProfileActivity.class);
//                startActivity(intent2);
//                return true;
            case R.id.action_booking_list:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intent3 = new Intent(getApplicationContext(),BookingListActivity.class);
                startActivity(intent3);
                return true;

            case R.id.action_account:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intent4 = new Intent(getApplicationContext(),AccountActivity.class);
                startActivity(intent4);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}
