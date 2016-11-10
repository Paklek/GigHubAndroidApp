package com.gighub.app.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.DataObject;
import com.gighub.app.model.Gig;
import com.gighub.app.model.GigResponse;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.MusicianResponse;
import com.gighub.app.model.RetrofitService;
import com.gighub.app.model.ServiceGighub;
import com.gighub.app.model.UserModel;
import com.gighub.app.model.UserResponse;
import com.gighub.app.ui.activity.BookMusicianActivity;
import com.gighub.app.ui.activity.GigActivity;
import com.gighub.app.ui.adapter.ListDiscoverGigAdapter;
import com.gighub.app.util.BuildUrl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverGigFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView img_gig;
    private FrameLayout frameLayout;
    private String mLokasi;

    Context mContext;
    public DiscoverGigFragment() {
        // Required empty public constructor
    }

//    List<UserModel> daftarUser = new ArrayList<UserModel>();
//    List<MusicianModel> mDaftarMusician = new ArrayList<MusicianModel>();
    List<Gig> mGig = new ArrayList<Gig>();
    TextView twName;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_discover_gig_main, container, false);

        mContext = getContext();
        twName = (TextView)view.findViewById(R.id.musician_name);

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        Call<GigResponse> call = buildUrl.serviceGighub.loadGig();

        buildUrl.serviceGighub.loadGig().enqueue(new Callback<GigResponse>() {
            @Override
            public void onResponse(Call<GigResponse> call, Response<GigResponse> response) {
                if(response.body().getError()==0){
                    mGig = response.body().getGigs();
                    mAdapter = new ListDiscoverGigAdapter(mGig);
                    mRecyclerView.setAdapter(mAdapter);
                    String tmp = "";
                    for(int i =0 ;i<mGig.size();i++)
                    {
                        tmp += String.format(""+ mGig.get(i).getNama_gig());
//                        mLokasi = mGig.get(i).getLokasi();
                    }


                }

            }

            @Override
            public void onFailure(Call<GigResponse> call, Throwable t) {
                Toast.makeText(mContext,"Connection Fail. Check Your Connection",Toast.LENGTH_LONG).show();
//                Log.e("-:failure",t.getMessage());
            }
        });

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.43.152:81/gighub2/public/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

//        ServiceGighub serviceGighub = retrofit.create(ServiceGighub.class);
//        Call<UserResponse> call = serviceGighub.loadUser();
//        RetrofitService.getInstance().getApi().loadMusicians().enqueue(new Callback<MusicianResponse>() {
//            @Override
//            public void onResponse(Call<MusicianResponse> call, Response<MusicianResponse> response) {
//                if(response.body().getError()==0){
//                    mDaftarMusician = response.body().getMusicians();
//                    mAdapter = new ListDiscoverGigAdapter(mDaftarMusician);
//                    mRecyclerView.setAdapter(mAdapter);
//                    String tmp = "";
//                    for(int i =0 ;i<mDaftarMusician.size();i++)
//                    {
//                        tmp += String.format(""+ mDaftarMusician.get(i).getName().toString());
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MusicianResponse> call, Throwable t) {
//                Toast.makeText(mContext,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
////                Log.e("-:failure",t.getMessage());
//            }
//        });


        img_gig = (ImageView)view.findViewById(R.id.img_img_gig_discovergig);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_musician_list);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);



//        img_gig.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), GigActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((ListDiscoverGigAdapter) mAdapter).setOnItemClickListener(new ListDiscoverGigAdapter().MyClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                Log.i(LOG_TAG, " Clicked on Item " + position);
//            }
//        });
    }

//    private ArrayList<DataObject> getDataSet() {
//        ArrayList results = new ArrayList<DataObject>();
//        for (int index = 0; index < 20; index++) {
//            DataObject obj = new DataObject("Some Primary Text " + index,
//                    "Secondary " + index);
//            results.add(index, obj);
//        }
//        return results;
//    }

}
