package com.gighub.app.ui.activity;

import android.app.ProgressDialog;
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
import com.gighub.app.ui.fragment.CompletedBookingListFragment;
import com.gighub.app.ui.fragment.OnProccessBookingListFragment;
import com.gighub.app.ui.fragment.RequestBookingListFragment;

public class BookingListActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            mToolbar = (Toolbar)findViewById(R.id.toolbar);
            mToolbar.setTitle("BOOKING LIST");
            setSupportActionBar(mToolbar);
            // my_child_mToolbar is defined in the layout file

//            setSupportActionBar(mToolbar);

            // Get a support ActionBar corresponding to this mToolbar
//            ActionBar ab = getSupportActionBar();

            // Enable the Up button
//            ab.setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); -- Tombol Back

            mViewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(mViewPager);

            mTabLayout = (TabLayout) findViewById(R.id.tabs);
            mTabLayout.setupWithViewPager(mViewPager);

//        ProgressDialog dialog = new ProgressDialog(this); // this = YourActivity
//        if (dialog.isShowing()){
//            dialog.cancel();
//        }

    }

    private void setupViewPager(ViewPager mViewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RequestBookingListFragment(), "REQUEST");
        adapter.addFragment(new OnProccessBookingListFragment(), "ON PROCCESS");
        adapter.addFragment(new CompletedBookingListFragment(), "COMPLETED");
        mViewPager.setAdapter(adapter);
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
                Intent intent = new Intent(getApplicationContext(),RegisterAsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_login:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent1 = new Intent(getApplicationContext(),LoginAsActivity.class);
                startActivity(intent1);
                return true;

//            case R.id.action_about:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                Intent intent2 = new Intent(getApplicationContext(),AboutActivity.class);
//                startActivity(intent2);
//                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
