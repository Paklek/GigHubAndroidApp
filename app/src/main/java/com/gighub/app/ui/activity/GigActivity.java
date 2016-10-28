package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.gighub.app.R;
import com.gighub.app.ui.adapter.MainViewPagerAdapter;
import com.gighub.app.ui.fragment.GigProfileFragment;
import com.gighub.app.ui.fragment.GigUpcomingFragment;
import com.gighub.app.util.CloudinaryUrl;
import com.squareup.picasso.Picasso;

public class GigActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mImageViewGig;
    private String mNamaGig, mPhotoGig;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig);

        mImageViewGig = (ImageView)findViewById(R.id.img_img_gig_gigactivity);
        Intent intent = getIntent();
        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        mPhotoGig = intent.getStringExtra("photo_gig");
        mNamaGig = intent.getStringExtra("nama_gig");

        mToolbar = (Toolbar)findViewById(R.id.toolbar);

        mToolbar.setTitle(mNamaGig);
        setSupportActionBar(mToolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); -- Tombol Back

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        if(mPhotoGig!=null && !mPhotoGig.equals("")) {
            Picasso.with(this).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPhotoGig)).into(mImageViewGig);
        }

    }

    private void setupViewPager(ViewPager mViewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GigProfileFragment(), "PROFILE");
        adapter.addFragment(new GigUpcomingFragment(), "UPCOMING");
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
