package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gighub.app.R;
import com.gighub.app.model.YourGig;
import com.gighub.app.ui.adapter.ListYourGigAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class YourGigActivity extends AppCompatActivity {

    private ListView mListView;
    private Toolbar mToolbar;
    private List<YourGig> mListYourGig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_gig);

        mListYourGig = new ArrayList<YourGig>();

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("Your Gigs");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        final Type type = new TypeToken<List<YourGig>>(){}.getType();

        mListYourGig = new Gson().fromJson(intent.getStringExtra("yourgigs"),type);

        mListView =  (ListView)findViewById(R.id.lv_yourgigs);
        mListView.setAdapter(new ListYourGigAdapter(getApplicationContext(),mListYourGig));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(getApplicationContext(),GigActivity.class);
                intent1.putExtra("nama_gig",mListYourGig.get(position).getNama_gig());
            }
        });


    }
}
