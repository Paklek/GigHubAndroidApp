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
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.Member;
import com.gighub.app.model.MemberResponse;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.adapter.ListSearchResultAdapter;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Member mMember;

//    public static int[] listImages = {R.drawable.ava, R.drawable.ava,R.drawable.ava,R.drawable.ava,R.drawable.ava,R.drawable.ava,R.drawable.ava};
//    public static String[] listNama = {"Nama Musisi1","Nama Musisi2","Nama Musisi3","Nama Musisi4","Nama Musisi5","Nama Musisi6","Nama Musisi7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
//        mListMusician = new ArrayList<MusicianModel>();
        mSearchResult = new ArrayList<SearchResultModel>();
        mMember = new Member();
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
//        final Type typemember = new TypeToken<Member>(){}.getType();
        mSearchResult = new Gson().fromJson(i.getStringExtra("search"),type);
//        mMember = new Gson().fromJson(i.getStringExtra("search"),typemember);

        mListView = (ListView)findViewById(R.id.lv_search);
        mListView.setAdapter(new ListSearchResultAdapter(getApplicationContext(),mSearchResult ));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String name,deskripsi,genre,harga,kota,tipe,photo,youtube_video, url_website, url_soundcloud, url_reverbnation;
                final int mId;
                mId = mSearchResult.get(position).getId();
                name = mSearchResult.get(position).getName();
                deskripsi = mSearchResult.get(position).getDeskripsi();
                genre = mSearchResult.get(position).getGenrenya();
                harga = mSearchResult.get(position).getHarga_sewa();
                kota = mSearchResult.get(position).getKota();
                tipe = mSearchResult.get(position).getTipe();
                photo = mSearchResult.get(position).getPhoto();
                youtube_video = mSearchResult.get(position).getYoutube_video();
                url_website = mSearchResult.get(position).getUrl_website();
                url_soundcloud = mSearchResult.get(position).getUsername_soundcloud();
                url_reverbnation = mSearchResult.get(position).getUsername_reverbnation();

                if (mSession.checkUserType().equals("msc") || mSession.checkUserType().equals("org")) {
                    BuildUrl buildUrl = new BuildUrl();
                    buildUrl.buildBaseUrl();

//                    Intent intent = new Intent(getApplicationContext(), MusicianActivity.class);
//                    intent.putExtra("id", mSearchResult.get(position).getId());
//                    intent.putExtra("name", mSearchResult.get(position).getName());
//                    intent.putExtra("deskripsi", mSearchResult.get(position).getDeskripsi());
//                    intent.putExtra("genre", mSearchResult.get(position).getGenrenya());
//                    intent.putExtra("harga_sewa", mSearchResult.get(position).getHarga_sewa());
//                    intent.putExtra("kota", mSearchResult.get(position).getKota());
//                    intent.putExtra("tipe", mSearchResult.get(position).getTipe());
//                    intent.putExtra("photo", mSearchResult.get(position).getPhoto());
//                    intent.putExtra("youtube_video",mSearchResult.get(position).getYoutube_video());
////                pos = position;
//                    intent.putExtra("posisi", position);
//                    Log.d("pos", "" + position);
//                    Log.d("response", "" + mSearchResult.get(position).getDeskripsi());
//                    Log.d("response", "id musisinya adalah " + mSearchResult.get(position).getId() + " dengan tipe " + mSearchResult.get(position).getTipe());
//                    startActivity(intent);


//                    BuildUrl buildUrl = new BuildUrl();
//                    buildUrl.buildBaseUrl();
                    if(tipe.equals("Group")) {
                        Map<String, String> dataCallMember = new HashMap<String, String>();
                        Log.d("id", Integer.toString(mSearchResult.get(position).getId()));
                        dataCallMember.put("id", Integer.toString(mSearchResult.get(position).getId()));
                        buildUrl.serviceGighub.sendCallMember(dataCallMember).enqueue(new Callback<MemberResponse>() {
                            @Override
                            public void onResponse(Call<MemberResponse> call, Response<MemberResponse> response) {
                                Intent intent = new Intent(getApplicationContext(), MusicianActivity.class);
//                                intent.putExtra("posisimember",mMember.getPosisi());
//                                intent.putExtra("anggotamember",mMember.getAnggota());
                                intent.putExtra("member", new Gson().toJson(response.body().getMember()));
                                intent.putExtra("id", mId);
                                intent.putExtra("name", name);
                                intent.putExtra("deskripsi", deskripsi);
                                intent.putExtra("genre", genre);
                                intent.putExtra("harga_sewa", harga);
                                intent.putExtra("kota", kota);
                                intent.putExtra("tipe", tipe);
                                intent.putExtra("photo", photo);
                                intent.putExtra("youtube_video", youtube_video);
                                intent.putExtra("url_website", url_website);
                                intent.putExtra("username_soundcloud", url_soundcloud);
                                intent.putExtra("username_reverbnation", url_reverbnation);
                                Log.d("member", response.body().getMember().toString());
                                Toast.makeText(SearchResultActivity.this, response.body().getMember().toString(), Toast.LENGTH_SHORT).show();
//                pos = position;
//                                intent.putExtra("posisi", position);
//                                Log.d("pos", "" + position);
//                                Log.d("response", "" + mSearchResult.get(position).getDeskripsi());
//                                Log.d("response", "id musisinya adalah " + mSearchResult.get(position).getId() + " dengan tipe " + mSearchResult.get(position).getTipe());

                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<MemberResponse> call, Throwable t) {

                            }
                        });
                    }
                        Intent intent = new Intent(getApplicationContext(), MusicianActivity.class);
                        intent.putExtra("id", mSearchResult.get(position).getId());
                        intent.putExtra("name", mSearchResult.get(position).getName());
                        intent.putExtra("deskripsi", mSearchResult.get(position).getDeskripsi());
                        intent.putExtra("genre", mSearchResult.get(position).getGenrenya());
                        intent.putExtra("harga_sewa", mSearchResult.get(position).getHarga_sewa());
                        intent.putExtra("kota", mSearchResult.get(position).getKota());
                        intent.putExtra("tipe", mSearchResult.get(position).getTipe());
                        intent.putExtra("photo", mSearchResult.get(position).getPhoto());
                        intent.putExtra("youtube_video", mSearchResult.get(position).getYoutube_video());
//                pos = position;
                        intent.putExtra("posisi", position);
                        Log.d("pos", "" + position);
                        Log.d("response", "" + mSearchResult.get(position).getDeskripsi());
                        Log.d("response", "id musisinya adalah " + mSearchResult.get(position).getId() + " dengan tipe " + mSearchResult.get(position).getTipe());
                        startActivity(intent);


                }
                else {
                        if (tipe.equals("Group")) {
                            BuildUrl buildUrl = new BuildUrl();
                            buildUrl.buildBaseUrl();
                            Map<String, String> dataCallMember = new HashMap<String, String>();
                            Log.d("id", Integer.toString(mSearchResult.get(position).getId()));
                            dataCallMember.put("id", Integer.toString(mSearchResult.get(position).getId()));
                            buildUrl.serviceGighub.sendCallMember(dataCallMember).enqueue(new Callback<MemberResponse>() {
                                @Override
                                public void onResponse(Call<MemberResponse> call, Response<MemberResponse> response) {
                                    Intent intent = new Intent(getApplicationContext(), MusicianActivity.class);
//                                intent.putExtra("posisimember",mMember.getPosisi());
//                                intent.putExtra("anggotamember",mMember.getAnggota());
                                    intent.putExtra("member", new Gson().toJson(response.body().getMember()));
                                    intent.putExtra("id", mId);
                                    intent.putExtra("name", name);
                                    intent.putExtra("deskripsi", deskripsi);
                                    intent.putExtra("genre", genre);
                                    intent.putExtra("harga_sewa", harga);
                                    intent.putExtra("kota", kota);
                                    intent.putExtra("tipe", tipe);
                                    intent.putExtra("photo", photo);
                                    intent.putExtra("youtube_video", youtube_video);
                                    Log.d("member", response.body().getMember().toString());
                                    Toast.makeText(SearchResultActivity.this, response.body().getMember().toString(), Toast.LENGTH_SHORT).show();
//                pos = position;
//                                intent.putExtra("posisi", position);
//                                Log.d("pos", "" + position);
//                                Log.d("response", "" + mSearchResult.get(position).getDeskripsi());
//                                Log.d("response", "id musisinya adalah " + mSearchResult.get(position).getId() + " dengan tipe " + mSearchResult.get(position).getTipe());

                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<MemberResponse> call, Throwable t) {

                                }
                            });
                        }

                        Intent intent = new Intent(getApplicationContext(), MusicianActivity.class);
                        intent.putExtra("id", mSearchResult.get(position).getId());
                        intent.putExtra("name", mSearchResult.get(position).getName());
                        intent.putExtra("deskripsi", mSearchResult.get(position).getDeskripsi());
                        intent.putExtra("genre", mSearchResult.get(position).getGenrenya());
                        intent.putExtra("harga_sewa", mSearchResult.get(position).getHarga_sewa());
                        intent.putExtra("kota", mSearchResult.get(position).getKota());
                        intent.putExtra("tipe", mSearchResult.get(position).getTipe());
                        intent.putExtra("photo", mSearchResult.get(position).getPhoto());
                        intent.putExtra("youtube_video", mSearchResult.get(position).getYoutube_video());
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
