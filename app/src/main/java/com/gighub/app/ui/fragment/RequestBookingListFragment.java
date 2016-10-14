package com.gighub.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gighub.app.R;
import com.gighub.app.model.Gig;
import com.gighub.app.model.SearchResultModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestBookingListFragment extends Fragment {


    private List<Gig> mGig;

    public RequestBookingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_request_booking_list, container, false);

        mGig = new ArrayList<Gig>();
        Intent i = getActivity().getIntent();

        final Type type = new TypeToken<List<Gig>>(){}.getType();
        mGig = new Gson().fromJson(i.getStringExtra("onreq"),type);

        return view;
    }

}
