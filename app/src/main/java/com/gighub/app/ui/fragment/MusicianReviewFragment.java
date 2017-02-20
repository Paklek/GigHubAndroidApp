package com.gighub.app.ui.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.ListReviewerResponse;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.model.YourReview;
import com.gighub.app.ui.adapter.ListReviewAdapter;
import com.gighub.app.util.BuildUrl;
import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
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
public class MusicianReviewFragment extends Fragment {

    private ListView mListView;
    private List<YourReview> mReviews;
    private List<SearchResultModel> mSearchResult;
    private int mMusicianId;
    private Context mContext;
    private LinearLayout mLinearLayoutNoReviews;
    private ProgressDialog mProgressDialog;
    private String mTipe;

    public MusicianReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_musician_review, container, false);


        mContext = getActivity().getApplicationContext();
        mProgressDialog = new ProgressDialog(getActivity());
        final Intent intent = getActivity().getIntent();

        mProgressDialog.setMessage("Load Reviews...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mMusicianId = intent.getIntExtra("id",0);
        mTipe = intent.getStringExtra("tipe");

        mReviews = new ArrayList<YourReview>();
        mSearchResult = new ArrayList<SearchResultModel>();
        mListView = (ListView)view.findViewById(R.id.lv_review_musicianfragment);
        mLinearLayoutNoReviews = (LinearLayout)view.findViewById(R.id.ll_no_review_musicianreview);

        final  Type type2 = new TypeToken<List<SearchResultModel>>(){}.getType();
        mSearchResult = new Gson().fromJson(intent.getStringExtra("search"),type2);


        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
        Map<String, String> dataForReviewer = new HashMap<>();
        dataForReviewer.put("musician_id",Integer.toString(mMusicianId));
        dataForReviewer.put("tipe",mTipe);
        final Type type = new TypeToken<List<YourReview>>(){}.getType();


        buildUrl.serviceGighub.sendReviewerData(dataForReviewer).enqueue(new Callback<ListReviewerResponse>() {
            @Override
            public void onResponse(Call<ListReviewerResponse> call, Response<ListReviewerResponse> response) {
                if (response.body().getError()==0){
                    mProgressDialog.show();
                    mReviews = response.body().getYourReviews();
                    if (mReviews.size()==0){
                        mLinearLayoutNoReviews.setVisibility(View.VISIBLE);
                        mProgressDialog.dismiss();
                    }
                    else {
                        mLinearLayoutNoReviews.setVisibility(View.GONE);
//                        for (int i =0 ; i< mReviews.size();i++) {
                            mListView.setAdapter(new ListReviewAdapter(getActivity().getApplicationContext(), mReviews));
//                        }
                        mProgressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListReviewerResponse> call, Throwable t) {
                Toast.makeText(mContext, "Connection Fail. Check Your Connection", Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
