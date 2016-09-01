package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gighub.app.R;
import com.gighub.app.ui.adapter.MainViewPagerAdapter;
import com.gighub.app.ui.fragment.DiscoverGigFragment;
import com.gighub.app.ui.fragment.DiscoverMusicianFragment;
import com.gighub.app.util.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager session;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String name;

    public static final String FIRST_NAME = "fname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());

        session.getSkipIntroStatus();

        if(session.isSkipIntroStatus()){

            toolbar = (Toolbar)findViewById(R.id.toolbar);
            if(session.isLoggedIn()){
                if(session.checkUserType().equals("org")){
                    name = session.getUserDetails().getFirst_name();
                }
                else if(session.checkUserType().equals("msc")){
                    name = session.getMusicianDetails().getName();
                }
                toolbar.setTitle("GigHub - "+name);
            }
            else {
                toolbar.setTitle("Gig Hub - Discover");
            }
            setSupportActionBar(toolbar);
            // my_child_toolbar is defined in the layout file

//            setSupportActionBar(toolbar);

            // Get a support ActionBar corresponding to this toolbar
//            ActionBar ab = getSupportActionBar();

            // Enable the Up button
//            ab.setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); -- Tombol Back

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);


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

        if(session.isLoggedIn()){
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
                Intent intent = new Intent(getApplicationContext(),JoinAsActivity.class);
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
