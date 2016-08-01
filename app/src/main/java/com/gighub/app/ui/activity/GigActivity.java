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
import com.gighub.app.ui.fragment.GigProfileFragment;
import com.gighub.app.ui.fragment.GigUpcomingFragment;
import com.gighub.app.ui.fragment.MusicianMusicFragment;
import com.gighub.app.ui.fragment.MusicianProfileFragment;
import com.gighub.app.ui.fragment.MusicianReviewFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class GigActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Gig");
        setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); -- Tombol Back

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GigProfileFragment(), "PROFILE");
        adapter.addFragment(new GigUpcomingFragment(), "UPCOMING");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_register:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_sign_in:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent1 = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent1);
                return true;

            case R.id.action_booking_list:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
                Intent intent2 = new Intent(getApplicationContext(),BookingListActivity.class);
                startActivity(intent2);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
