package com.gighub.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.GigOfferMusicianResponse;
import com.gighub.app.ui.activity.GigOfferMusicianActivity;
import com.gighub.app.ui.activity.ViewMapGig;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GigProfileFragment extends Fragment {


    public GigProfileFragment() {
        // Required empty public constructor
    }


    private View view;
    private Button btn_viewMapGig, mButtonGigOffer;
    private TextView mTextViewLocation, mTextViewLocationDetail, mTextViewDeskripsi, mTextViewTanggalMulai, mTextViewTanggalSelesai;
    private SessionManager mSession;
    private int mMusicianId, mOrganizerId, mGigId;
    private String tanggal_mulai, tanggal_selesai,mLat,mLng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gig_profile, container, false);


        mSession = new SessionManager(getActivity().getApplicationContext());
        btn_viewMapGig = (Button)view.findViewById(R.id.btn_view_map);
        mTextViewLocation = (TextView)view.findViewById(R.id.tv_location_gigprofile);
        mTextViewLocationDetail = (TextView)view.findViewById(R.id.tv_location_detail_gigprofile);
        mTextViewDeskripsi = (TextView)view.findViewById(R.id.tv_deskripsi_gigprofile);
        mTextViewTanggalMulai = (TextView)view.findViewById(R.id.tv_tanggal_mulai_gigprofile);
        mTextViewTanggalSelesai = (TextView)view.findViewById(R.id.tv_tanggal_selesai_gigprofile);

        mButtonGigOffer = (Button)view.findViewById(R.id.btn_offer_gig_gigprofile);

        mMusicianId = mSession.getMusicianDetails().getId();
        Intent intent1 = new Intent(getActivity().getIntent());

        tanggal_mulai = intent1.getStringExtra("tanggal_mulai");
        tanggal_selesai = intent1.getStringExtra("tanggal_selesai");
        mOrganizerId = intent1.getIntExtra("user_id",0);
        mGigId = intent1.getIntExtra("gig_id",0);

        mLat = intent1.getStringExtra("lat");
        mLng = intent1.getStringExtra("lng");

        mTextViewLocation.setText("Location : "+intent1.getStringExtra("lokasi"));
        mTextViewLocationDetail.setText("Location Detail : "+intent1.getStringExtra("lokasi_detail"));
        mTextViewDeskripsi.setText("Deskripsi : "+intent1.getStringExtra("deskripsi"));
        mTextViewTanggalMulai.setText("Start Date : "+tanggal_mulai);
        mTextViewTanggalSelesai.setText("Finish Date : "+tanggal_selesai);


        if (mSession.checkUserType().equals("org")){
            mButtonGigOffer.setVisibility(View.GONE);
        }


        mButtonGigOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendOfferMusician();
//                startActivity(intent2);
            }
        });

        btn_viewMapGig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ViewMapGig.class);
                intent.putExtra("lat",mLat);
                intent.putExtra("lng",mLng);
                startActivity(intent);
            }
        });

        return view;
    }

    private void sendOfferMusician(){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
        Map<String, String> offerMusicianData = new HashMap<>();
        offerMusicianData.put("user_id",Integer.toString(mMusicianId));
        buildUrl.serviceGighub.sendGigOfferData(offerMusicianData).enqueue(new Callback<GigOfferMusicianResponse>() {
            @Override
            public void onResponse(Call<GigOfferMusicianResponse> call, Response<GigOfferMusicianResponse> response) {
                Intent intent2 = new Intent(getActivity(), GigOfferMusicianActivity.class);
                intent2.putExtra("gigoffermusician", new Gson().toJson(response.body().getGigOfferMusicianList()));
                intent2.putExtra("organizer_id",mOrganizerId);
                intent2.putExtra("gig_id",mGigId);
                intent2.putExtra("tanggal_mulai",tanggal_mulai);
                intent2.putExtra("tanggal_selesai",tanggal_selesai);
                startActivity(intent2);
            }

            @Override
            public void onFailure(Call<GigOfferMusicianResponse> call, Throwable t) {

            }
        });
    }

}
