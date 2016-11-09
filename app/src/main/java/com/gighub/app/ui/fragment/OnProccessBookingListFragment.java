package com.gighub.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Penyewaan;
import com.gighub.app.model.PenyewaanResponse;
import com.gighub.app.ui.activity.BookingDetailsActivity;
import com.gighub.app.ui.adapter.ListOnProccessBookingAdapter;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnProccessBookingListFragment extends Fragment {

    private List<Penyewaan> mPenyewaan;
    private ListView mListView;
    private TextView mTexViewName;
    private BaseAdapter mAdapter;
    private int mIdUser;
    private String mTipeUser;
    private SessionManager mSession;

    public OnProccessBookingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_on_proccess_book_list, container, false);

        mSession = new SessionManager(getActivity().getApplicationContext());

        if(mSession.isLoggedIn()){
            if(mSession.checkUserType().equals("org")){
                mIdUser = mSession.getUserDetails().getId();
                mTipeUser = "org";
            }
            else if(mSession.checkUserType().equals("msc")){
                mIdUser = mSession.getMusicianDetails().getId();
                mTipeUser = "msc";
            }

        }

        mPenyewaan = new ArrayList<Penyewaan>();
//        Intent i = getActivity().getIntent();
        mTexViewName = (TextView)view.findViewById(R.id.tv_musician_name_onproccess);

//        final Type type = new TypeToken<List<Penyewaan>>(){}.getType();
//        mPenyewaan = new Gson().fromJson(i.getStringExtra("onproc"),type);
        mListView = (ListView)view.findViewById(R.id.lv_book_on_proc);
//        mListView.setAdapter(new ListOnProccessBookingAdapter(getActivity().getApplicationContext(),mPenyewaan));
        onProccess(mIdUser,mTipeUser);

//        if(!mSession.checkUserType().equals("msc")) {
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), BookingDetailsActivity.class);

                    intent.putExtra("nama_musisi", mPenyewaan.get(position).getName());
                    intent.putExtra("nama_gig", mPenyewaan.get(position).getNama_gig());
                    intent.putExtra("nama_user", mPenyewaan.get(position).getFirst_name() + " " + mPenyewaan.get(position).getLast_name());
                    intent.putExtra("lokasi", mPenyewaan.get(position).getLokasi());
//                    intent.putExtra("harga", mPenyewaan.get(position).getHarga());
                    intent.putExtra("harga_sewa", mPenyewaan.get(position).getHarga_sewa());
                    intent.putExtra("waktu_mulai", mPenyewaan.get(position).getTanggal_mulai());
                    intent.putExtra("waktu_selesai", mPenyewaan.get(position).getTanggal_selesai());
                    intent.putExtra("total", mPenyewaan.get(position).getTotal_biaya());
                    intent.putExtra("sewa_id", mPenyewaan.get(position).getId());
                    intent.putExtra("type_sewa", mPenyewaan.get(position).getType_sewa());
                    intent.putExtra("type_gig", mPenyewaan.get(position).getType_gig());
                    intent.putExtra("photo", mPenyewaan.get(position).getPhoto());
                    intent.putExtra("photo_gig", mPenyewaan.get(position).getPhoto_gig());
                    intent.putExtra("status", mPenyewaan.get(position).getStatus());
                    intent.putExtra("status_request", mPenyewaan.get(position).getStatus_request());
                    intent.putExtra("activity","onproccessbooking");

                    startActivity(intent);
                }
            });
//        }

        return view;
    }


    Map<String,String> forOnProccess = new HashMap<>();
    private void onProccess(int userId, String tipeUser){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        forOnProccess.put("tipe_user", tipeUser);
        forOnProccess.put("user_id",Integer.toString(userId));

        buildUrl.serviceGighub.sendForOnProccessBook(forOnProccess).enqueue(new Callback<PenyewaanResponse>() {
            @Override
            public void onResponse(Call<PenyewaanResponse> call, Response<PenyewaanResponse> response) {
                if(response.body().getError()==0){
                    mPenyewaan = response.body().getPenyewaanList();
                    mAdapter = new ListOnProccessBookingAdapter(getContext(),mPenyewaan);
                    mListView.setAdapter(mAdapter);
                    String tmp = "";
                    for (int i=0;i<mListView.getCount();i++){
//                        mTexViewName.setText(mPenyewaan.get(i).getFirst_name());
                        tmp+= String.format("",mPenyewaan.get(i).getFirst_name());
                    }
                }
            }

            @Override
            public void onFailure(Call<PenyewaanResponse> call, Throwable t) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }

}
