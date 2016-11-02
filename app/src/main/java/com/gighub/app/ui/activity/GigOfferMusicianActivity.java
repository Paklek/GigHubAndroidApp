package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gighub.app.R;
import com.gighub.app.model.GigOfferMusician;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.adapter.ListGigOfferMusicianAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GigOfferMusicianActivity extends AppCompatActivity {

    private ListView mListView;
    private int object_id,subject_id, gig_id;
    private List<GigOfferMusician> mGigOfferMusicianList;
    private String tanggal_mulai, tanggal_selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_gig_offer_musician);


        final Intent intent = new Intent(getIntent());
        mGigOfferMusicianList = new ArrayList<GigOfferMusician>();

        final Type type = new TypeToken<List<GigOfferMusician>>(){}.getType();
        mGigOfferMusicianList = new Gson().fromJson(intent.getStringExtra("gigoffermusician"),type);

        object_id = intent.getIntExtra("organizer_id",0);
        gig_id = intent.getIntExtra("gig_id",0);
        tanggal_mulai = intent.getStringExtra("tanggal_mulai");
        tanggal_selesai = intent.getStringExtra("tanggal_selesai");

        mListView = (ListView)findViewById(R.id.lv_gig_offer_musician);
        mListView.setAdapter(new ListGigOfferMusicianAdapter(getApplicationContext(),mGigOfferMusicianList));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(getApplicationContext(),GigOfferMusicianItemActivity.class);
                intent1.putExtra("id",mGigOfferMusicianList.get(position).getId());
                intent1.putExtra("object_id",object_id);
                intent1.putExtra("gig_id",gig_id);
                intent1.putExtra("tanggal_mulai", tanggal_mulai);
                intent1.putExtra("tanggal_selesai", tanggal_selesai);

                intent1.putExtra("name", mGigOfferMusicianList.get(position).getName());
                intent1.putExtra("genrenya", mGigOfferMusicianList.get(position).getGenrenya());
                intent1.putExtra("harga",mGigOfferMusicianList.get(position).getHarga_sewa());
                intent1.putExtra("deskripsi", mGigOfferMusicianList.get(position).getDeskripsi());
                intent1.putExtra("photo",mGigOfferMusicianList.get(position).getPhoto());
                intent1.putExtra("kota",mGigOfferMusicianList.get(position).getKota());
                intent1.putExtra("tipe",mGigOfferMusicianList.get(position).getTipe());
                startActivity(intent1);
            }
        });
    }
}
