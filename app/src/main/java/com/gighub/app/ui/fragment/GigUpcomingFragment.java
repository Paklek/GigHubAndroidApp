package com.gighub.app.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gighub.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GigUpcomingFragment extends Fragment {


    public GigUpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gig_upcoming, container, false);
    }

}
