package com.gighub.app.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.gighub.app.R;
import com.gighub.app.model.Penyewaan;
import com.gighub.app.model.PenyewaanResponse;
import com.gighub.app.ui.adapter.ListCompletedBookingAdapter;
import com.gighub.app.ui.adapter.ListOnProccessBookingAdapter;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;

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
public class CompletedBookingListFragment extends Fragment {

    private List<Penyewaan> mPenyewaan;
    private SessionManager mSession;
    private BaseAdapter mAdapter;
    private int mIdUser;
    private String mTipeUser;
    private ListView mListView;
    public CompletedBookingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_completed_booking_list, container, false);

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
        mListView = (ListView)v.findViewById(R.id.lv_book_completed);

        sendDataCompletedBook(mIdUser,mTipeUser);


        return v;
    }

    Map<String,String> dataCompletedBook = new HashMap<>();
    private void sendDataCompletedBook(int mIdUser, String mTipeUser){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        dataCompletedBook.put("tipe_user",mTipeUser);
        dataCompletedBook.put("user_id",Integer.toString(mIdUser));

        buildUrl.serviceGighub.sendCompletedBook(dataCompletedBook).enqueue(new Callback<PenyewaanResponse>() {
            @Override
            public void onResponse(Call<PenyewaanResponse> call, Response<PenyewaanResponse> response) {
                if(response.body().getError()==0){
                    mPenyewaan = response.body().getPenyewaanList();
                    mAdapter = new ListCompletedBookingAdapter(getContext(),mPenyewaan);
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
