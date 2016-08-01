package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gighub.app.R;
import com.gighub.app.ui.adapter.ListSearchResultAdapter;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    ListView lv;
    Context context;
    ArrayList arrayList;
    private Toolbar toolbar;

    public static int[] listImages = {R.drawable.ava, R.drawable.ava,R.drawable.ava,R.drawable.ava,R.drawable.ava,R.drawable.ava,R.drawable.ava};
    public static String[] listNama = {"Nama Musisi1","Nama Musisi2","Nama Musisi3","Nama Musisi4","Nama Musisi5","Nama Musisi6","Nama Musisi7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        context = this;
//        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Search Result");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
        lv = (ListView)findViewById(R.id.lv_search);
//        ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listNama);
//        lv.setAdapter(testAdapter);
        lv.setAdapter(new ListSearchResultAdapter(getApplicationContext(),listNama,listImages ));
//        context.startActivity(i);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),MusicianActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
