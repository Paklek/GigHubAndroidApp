package com.gighub.app.ui.fragment;


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

    public MusicianReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_musician_review, container, false);

        mContext = getActivity().getApplicationContext();
        final Intent intent = getActivity().getIntent();

        mMusicianId = intent.getIntExtra("id",0);

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
        final Type type = new TypeToken<List<YourReview>>(){}.getType();


        buildUrl.serviceGighub.sendReviewerData(dataForReviewer).enqueue(new Callback<ListReviewerResponse>() {
            @Override
            public void onResponse(Call<ListReviewerResponse> call, Response<ListReviewerResponse> response) {
                if (response.body().getError()==0){
                    mReviews = response.body().getYourReviews();
                    if (mReviews.size()==0){
                        mLinearLayoutNoReviews.setVisibility(View.VISIBLE);
                    }
                    else {
                        mListView.setAdapter(new ListReviewAdapter(getActivity().getApplicationContext(), mReviews));
                        mLinearLayoutNoReviews.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListReviewerResponse> call, Throwable t) {
                Toast.makeText(mContext, "Connection Fail. Check Your Connection", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
