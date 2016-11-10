package com.gighub.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.gighub.app.R;
import com.gighub.app.model.Gig;
import com.gighub.app.model.Penyewaan;
import com.gighub.app.model.PenyewaanResponse;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.activity.BookingDetailsActivity;
import com.gighub.app.ui.adapter.ListOnRequestBookingAdapter;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
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
public class RequestBookingListFragment extends Fragment {


    private List<Penyewaan> mPenyewaan;
    private ListView mListView;
    private BaseAdapter mAdapter;
    private SessionManager mSession;

    public RequestBookingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_request_booking_list, container, false);

        mPenyewaan = new ArrayList<Penyewaan>();
        Intent i = getActivity().getIntent();

        mSession = new SessionManager(getContext().getApplicationContext());
        final Type type = new TypeToken<List<Penyewaan>>(){}.getType();
        mPenyewaan = new Gson().fromJson(i.getStringExtra("onreq"),type);
//
        mAdapter = new ListOnRequestBookingAdapter(getActivity().getApplicationContext(),mPenyewaan);
//
        mListView = (ListView)view.findViewById(R.id.lv_book_req);
//
//        for (int j=0;j<mPenyewaan.size();j++){
//            if (mPenyewaan.get(j).getStatus().equals("1")){
//                mListView.setAdapter(null);
//            }
//            else {
//
//                mListView.setAdapter(mAdapter);
//            }
//        }

        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
//        if(mSession.checkUserType().equals("msc")) {
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
                    intent.putExtra("admin_id", mPenyewaan.get(position).getAdmin_id());
                    intent.putExtra("activity","onrequestbooking");

                    startActivity(intent);
                }
            });
//        }

        return view;
    }


    Map<String, String> idUser = new HashMap<>();
    Map<String,String> forOnProccess = new HashMap<>();
    private void Book(final Intent i, int userId, String tipeUser){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        idUser.put("tipe_user", tipeUser);
        idUser.put("user_id",Integer.toString(userId));
        buildUrl.serviceGighub.sendIdUserForBook(idUser).enqueue(new Callback<PenyewaanResponse>() {
            @Override
            public void onResponse(Call<PenyewaanResponse> call, Response<PenyewaanResponse> response) {
                i.putExtra("onreq", new Gson().toJson(response.body().getPenyewaanList()));

//                if(response.body().getError()==0){
//                    mAdapter = new ListOnRequestBookingAdapter();
//                }
            }

            @Override
            public void onFailure(Call<PenyewaanResponse> call, Throwable t) {

            }
        });

    }

}
