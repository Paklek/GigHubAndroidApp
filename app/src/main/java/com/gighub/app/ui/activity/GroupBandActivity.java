package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.gighub.app.R;
import com.gighub.app.model.GroupBand;
import com.gighub.app.ui.adapter.ListGrupBandAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GroupBandActivity extends AppCompatActivity {

    private ListView mListView;
    private Context mContext;
    private Toolbar mToolbar;

    private List<GroupBand> mListGroupBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grup_band);

        mListGroupBand = new ArrayList<GroupBand>();
        mContext = this;

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("GrupBands");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        final Type type = new TypeToken<List<GroupBand>>(){}.getType();

        mListGroupBand = new Gson().fromJson(intent.getStringExtra("bands"),type);

        mListView = (ListView)findViewById(R.id.lv_grupband);
        mListView.setAdapter(new ListGrupBandAdapter(getApplicationContext(),mListGroupBand));

    }
}
