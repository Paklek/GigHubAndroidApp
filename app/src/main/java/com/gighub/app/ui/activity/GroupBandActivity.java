package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gighub.app.R;
import com.gighub.app.model.GroupBand;
import com.gighub.app.model.YourBand;
import com.gighub.app.ui.adapter.ListGrupBandAdapter;
import com.gighub.app.ui.adapter.ListYourBandAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GroupBandActivity extends AppCompatActivity {

    private ListView mListView;
    private Context mContext;
    private Toolbar mToolbar;

    private List<YourBand> mListGroupBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grup_band);

        mListGroupBand = new ArrayList<YourBand>();
        mContext = this;

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("GrupBands");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        final Type type = new TypeToken<List<YourBand>>(){}.getType();
        mListGroupBand = new Gson().fromJson(intent.getStringExtra("bands"),type);

        mListView = (ListView)findViewById(R.id.lv_grupband);
        mListView.setAdapter(new ListYourBandAdapter(mContext,mListGroupBand));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Intent intent1 = new Intent(mContext,GroupBandProfileActivity.class);
                intent1.putExtra("anggota",mListGroupBand.get(position).getAnggota());
                intent1.putExtra("posisi", mListGroupBand.get(position).getPosisi());
                intent1.putExtra("photo", mListGroupBand.get(position).getPhoto());
                intent1.putExtra("cover",mListGroupBand.get(position).getCover());
                intent1.putExtra("kota", mListGroupBand.get(position).getKota());
                intent1.putExtra("harga", mListGroupBand.get(position).getHarga());
                intent1.putExtra("tipe", mListGroupBand.get(position).getTipe());
                intent1.putExtra("deskripsi",mListGroupBand.get(position).getDeskripsi());
                intent1.putExtra("nama_grupband", mListGroupBand.get(position).getNama_grupband());
                startActivity(intent1);
            }
        });


    }
}
