package com.gighub.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gighub.app.R;
import com.gighub.app.ui.activity.ViewMapGig;

/**
 * A simple {@link Fragment} subclass.
 */
public class GigProfileFragment extends Fragment {


    public GigProfileFragment() {
        // Required empty public constructor
    }


    private View view;
    private Button btn_viewMapGig;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gig_profile, container, false);

        btn_viewMapGig = (Button)view.findViewById(R.id.btn_view_map);

        btn_viewMapGig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewMapGig.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
