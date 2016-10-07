package com.gighub.app.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.gighub.app.R;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.adapter.ListDiscoverGigAdapter;
import com.gighub.app.ui.adapter.MainViewPagerAdapter;
import com.gighub.app.ui.fragment.MusicianMusicFragment;
import com.gighub.app.ui.fragment.MusicianProfileFragment;
import com.gighub.app.ui.fragment.MusicianReviewFragment;
import com.gighub.app.util.StaticString;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MusicianActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mImageViewProfile;
    private String mName;
    private int pos=0;
    private List<SearchResultModel> mSearchResultModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician);

        mSearchResultModels = new ArrayList<SearchResultModel>();

        mImageViewProfile = (ImageView)findViewById(R.id.img_img_profile_musicianactivity);


        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        pos = intent.getIntExtra("posisi",pos);
        final Type type = new TypeToken<List<SearchResultModel>>(){}.getType();
        mSearchResultModels = new Gson().fromJson(intent.getStringExtra("search"),type);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
//        Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(getApplicationContext()));

//        String mPhoto;
//        URL url = new URL()
//        Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        mImageViewProfile.setImageBitmap(url);
//
//
//        mPhoto = cloudinary.url().generate("default_user");
//
//        mImageViewProfile.;


        mToolbar.setTitle(""+mName);
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
