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

import com.cloudinary.Cloudinary;
import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.adapter.ListSearchResultAdapter;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    ListView mListView;
    Context mContext;
    private Toolbar toolbar;
    private MusicianModel mMusician;
    private String mNameMusician;
    private int pos=0;
    private SessionManager mSession;
//    private List<MusicianModel> mListMusician ;
    private List<SearchResultModel> mSearchResult;

//    public static int[] listImages = {R.drawable.ava, R.drawable.ava,R.drawable.ava,R.drawable.ava,R.drawable.ava,R.drawable.ava,R.drawable.ava};
//    public static String[] listNama = {"Nama Musisi1","Nama Musisi2","Nama Musisi3","Nama Musisi4","Nama Musisi5","Nama Musisi6","Nama Musisi7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
//        mListMusician = new ArrayList<MusicianModel>();
        mSearchResult = new ArrayList<SearchResultModel>();
        mSession = new SessionManager(getApplicationContext());

//        mContext =;
//        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Search Result");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
//        final Type typeListMusician = new TypeToken<List<MusicianModel>>(){}.getType();
//        mListMusician = new Gson().fromJson(i.getStringExtra("search"),typeListMusician);
        Intent i = getIntent();
        final Type type = new TypeToken<List<SearchResultModel>>(){}.getType();
        mSearchResult = new Gson().fromJson(i.getStringExtra("search"),type);

        mListView = (ListView)findViewById(R.id.lv_search);
        mListView.setAdapter(new ListSearchResultAdapter(getApplicationContext(),mSearchResult ));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mSession.checkUserType().equals("msc") || mSession.checkUserType().equals("org")) {
                    BuildUrl buildUrl = new BuildUrl();
                    buildUrl.buildBaseUrl();

                    Intent intent = new Intent(getApplicationContext(), MusicianActivity.class);
                    intent.putExtra("id", mSearchResult.get(position).getId());
                    intent.putExtra("name", mSearchResult.get(position).getName());
                    intent.putExtra("deskripsi", mSearchResult.get(position).getDeskripsi());
                    intent.putExtra("genre", mSearchResult.get(position).getGenrenya());
                    intent.putExtra("harga_sewa", mSearchResult.get(position).getHarga_sewa());
                    intent.putExtra("kota", mSearchResult.get(position).getKota());
                    intent.putExtra("tipe", mSearchResult.get(position).getTipe());
                    intent.putExtra("photo", mSearchResult.get(position).getPhoto());
//                pos = position;
                    intent.putExtra("posisi", position);
                    Log.d("pos", "" + position);
                    Log.d("response", "" + mSearchResult.get(position).getDeskripsi());
                    Log.d("response", "id musisinya adalah " + mSearchResult.get(position).getId() + " dengan tipe " + mSearchResult.get(position).getTipe());
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MusicianActivity.class);
                    intent.putExtra("id", mSearchResult.get(position).getId());
                    intent.putExtra("name", mSearchResult.get(position).getName());
                    intent.putExtra("deskripsi", mSearchResult.get(position).getDeskripsi());
                    intent.putExtra("genre", mSearchResult.get(position).getGenrenya());
                    intent.putExtra("harga_sewa", mSearchResult.get(position).getHarga_sewa());
                    intent.putExtra("kota", mSearchResult.get(position).getKota());
                    intent.putExtra("tipe", mSearchResult.get(position).getTipe());
                    intent.putExtra("photo", mSearchResult.get(position).getPhoto());
//                pos = position;
                    intent.putExtra("posisi", position);
                    Log.d("pos", "" + position);
                    Log.d("response", "" + mSearchResult.get(position).getDeskripsi());
                    Log.d("response", "id musisinya adalah " + mSearchResult.get(position).getId() + " dengan tipe " + mSearchResult.get(position).getTipe());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
