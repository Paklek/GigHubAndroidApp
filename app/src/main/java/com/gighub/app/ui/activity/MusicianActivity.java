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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gighub.app.R;
import com.gighub.app.ui.adapter.MainViewPagerAdapter;
import com.gighub.app.ui.fragment.DiscoverGigFragment;
import com.gighub.app.ui.fragment.DiscoverMusicianFragment;
import com.gighub.app.ui.fragment.MusicianMusicFragment;
import com.gighub.app.ui.fragment.MusicianProfileFragment;
import com.gighub.app.ui.fragment.MusicianReviewFragment;

public class MusicianActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician);


        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("Musician");
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
        adapter.addFragment(new MusicianMusicFragment(), "MUSICS");
        adapter.addFragment(new MusicianReviewFragment(), "REVIEWS");
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
                Intent intent = new Intent(getApplicationContext(),JoinAsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_login:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent1 = new Intent(getApplicationContext(),LoginAsActivity.class);
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
