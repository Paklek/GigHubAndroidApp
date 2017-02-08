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

import com.gighub.app.R;
import com.gighub.app.model.Penyewaan;
import com.gighub.app.model.PenyewaanResponse;
import com.gighub.app.model.YourReview;
import com.gighub.app.model.YourReviewResponse;
import com.gighub.app.ui.activity.BookingDetailsActivity;
import com.gighub.app.ui.activity.ReviewMusicianActivity;
import com.gighub.app.ui.adapter.ListCompletedBookingAdapter;
import com.gighub.app.ui.adapter.ListOnProccessBookingAdapter;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;

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
    private int mIdUser, mHargaSewa, mSewaId, mTotal;
    private String mTipeUser, mNamaMusisi, mNamaGig, mNamaUser, mLokasi, mWaktuMulai, mWaktuSelesai, mTypeGig, mTypeSewa, mPhotoGig, mPhotoMusisi, mStatus, mStatusRequest;
    private ListView mListView;
    private YourReview mYourReview;
    public CompletedBookingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_completed_booking_list, container, false);

        mSession = new SessionManager(getActivity().getApplicationContext());
        mYourReview = new YourReview();

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


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent(getActivity().getApplicationContext(), ReviewMusicianActivity.class);
                mNamaMusisi = mPenyewaan.get(position).getName();
                mNamaGig = mPenyewaan.get(position).getNama_gig();
                mNamaUser = mPenyewaan.get(position).getFirst_name()+" "+mPenyewaan.get(position).getLast_name();
                mLokasi = mPenyewaan.get(position).getLokasi();
                mHargaSewa = mPenyewaan.get(position).getHarga_sewa();
                mWaktuMulai = mPenyewaan.get(position).getTanggal_mulai();
                mWaktuSelesai = mPenyewaan.get(position).getTanggal_selesai();
                mTotal = mPenyewaan.get(position).getTotal_biaya();
                mSewaId = mPenyewaan.get(position).getId();
                mTypeSewa = mPenyewaan.get(position).getType_sewa();
                mTypeGig = mPenyewaan.get(position).getType_gig();
                mPhotoMusisi = mPenyewaan.get(position).getPhoto();
                mPhotoGig = mPenyewaan.get(position).getPhoto_gig();
                mStatus = mPenyewaan.get(position).getStatus();
                mStatusRequest = mPenyewaan.get(position).getStatus_request();

                if(mStatus.equals("4")&& (mTipeUser.equals("org") || mTipeUser.equals("msc"))){
                    BuildUrl buildUrl = new BuildUrl();
                    buildUrl.buildBaseUrl();
                    Map <String, String> dataYourReview = new HashMap<String, String>();
                    dataYourReview.put("sewa_id", Integer.toString(mPenyewaan.get(position).getId()));
                    buildUrl.serviceGighub.sendDataYourReview(dataYourReview).enqueue(new Callback<YourReviewResponse>() {
                        @Override
                        public void onResponse(Call<YourReviewResponse> call, Response<YourReviewResponse> response) {

                            intent.putExtra("yourreview",new Gson().toJson(response.body().getYourReview()));
                            intent.putExtra("nama_musisi", mNamaMusisi);
                            intent.putExtra("nama_gig", mNamaGig);
                            intent.putExtra("nama_user", mNamaUser);
                            intent.putExtra("lokasi", mLokasi);
//                    intent.putExtra("harga", mPenyewaan.get(position).getHarga());
                            intent.putExtra("harga_sewa", mHargaSewa);
                            intent.putExtra("waktu_mulai", mWaktuMulai);
                            intent.putExtra("waktu_selesai", mWaktuSelesai);
                            intent.putExtra("total", mTotal);
                            intent.putExtra("sewa_id", mSewaId);
                            intent.putExtra("type_sewa", mTypeSewa);
                            intent.putExtra("type_gig", mTypeGig);
                            intent.putExtra("photo", mPhotoMusisi);
                            intent.putExtra("photo_gig", mPhotoGig);
                            intent.putExtra("status", mStatus);
                            intent.putExtra("status_request", mStatusRequest);
                            intent.putExtra("activity","onproccessbooking");

                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<YourReviewResponse> call, Throwable t) {

                        }
                    });
                }

                else if(mStatus.equals("3") && mTipeUser.equals("org")) {
                    intent.putExtra("nama_musisi", mNamaMusisi);
                    intent.putExtra("nama_gig", mNamaGig);
                    intent.putExtra("nama_user", mNamaUser);
                    intent.putExtra("lokasi", mLokasi);
//                    intent.putExtra("harga", mPenyewaan.get(position).getHarga());
                    intent.putExtra("harga_sewa", mHargaSewa);
                    intent.putExtra("waktu_mulai", mWaktuMulai);
                    intent.putExtra("waktu_selesai", mWaktuSelesai);
                    intent.putExtra("total", mTotal);
                    intent.putExtra("sewa_id", mSewaId);
                    intent.putExtra("type_sewa", mTypeSewa);
                    intent.putExtra("type_gig", mTypeGig);
                    intent.putExtra("photo", mPhotoMusisi);
                    intent.putExtra("photo_gig", mPhotoGig);
                    intent.putExtra("status", mStatus);
                    intent.putExtra("status_request", mStatusRequest);
                    intent.putExtra("activity","onproccessbooking");
                    startActivity(intent);

                }

            }
        });

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
