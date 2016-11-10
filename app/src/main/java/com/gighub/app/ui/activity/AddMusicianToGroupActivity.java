package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gighub.app.R;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.PositionResponse;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.adapter.ListAddMusicianToGroupAdapter;
import com.gighub.app.util.BuildUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMusicianToGroupActivity extends AppCompatActivity {

    private ListView mListView;
    private Context mContext;

    private List<MusicianModel> mCalonAnggota;
    private int mGrupBandId,mAdminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_musician_to_group);

        mContext = getApplicationContext();

        mCalonAnggota = new ArrayList<MusicianModel>();

        Intent i = getIntent();
        final Type type = new TypeToken<List<MusicianModel>>(){}.getType();
        mCalonAnggota = new Gson().fromJson(i.getStringExtra("calonanggota"),type);

        mGrupBandId = i.getIntExtra("grupband_id",0);
        mAdminId = i.getIntExtra("admin_id",0);

        mListView = (ListView)findViewById(R.id.lv_add_anggota);
        mListView.setAdapter(new ListAddMusicianToGroupAdapter(mContext,mCalonAnggota));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();

                buildUrl.serviceGighub.callPosition().enqueue(new Callback<PositionResponse>() {
                    @Override
                    public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
                        Intent intent = new Intent(mContext, AddPositionMusicianActivity.class);
                        intent.putExtra("positions", new Gson().toJson(response.body().getPositions()));
                        intent.putExtra("name", mCalonAnggota.get(position).getName());
                        intent.putExtra("kota", mCalonAnggota.get(position).getKota());
                        intent.putExtra("basis", mCalonAnggota.get(position).getBasis());
                        intent.putExtra("calon_id", mCalonAnggota.get(position).getId());
                        intent.putExtra("grupband_id",mGrupBandId);
                        intent.putExtra("admin_id",mAdminId);
                        Log.d("response",response.body().toString());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<PositionResponse> call, Throwable t) {

                    }
                });

//                Intent intent = new Intent(mContext, AddPositionMusicianActivity.class);
//                intent.putExtra("name", mCalonAnggota.get(position).getName());
//                startActivity(intent);
            }
        });



    }
}
