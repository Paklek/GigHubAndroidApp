package com.gighub.app.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.gighub.app.R;
import com.gighub.app.ui.activity.MainActivity;
import com.gighub.app.ui.activity.SearchResultActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverMusicianFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Context context;


    public DiscoverMusicianFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover_musician_main, container, false);
        Button btn_search = (Button)view.findViewById(R.id.btn_search);

        context = inflater.getContext();


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SearchResultActivity.class);
                startActivity(i);
            }
        });


//        Spinner spinner = (Spinner) view.findViewById(R.id.provinsi_spinner);

//        masalah di getActivity, karena tadinya di activity work, trus pake "this" nah kalo di fragment gk work
        Spinner spinner1 = (Spinner)view.findViewById(R.id.provinsi_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.provinsi_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);



        Spinner spinner2 = (Spinner)view.findViewById(R.id.kota_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.city_array, R.layout.support_simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);


        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {

    }

    public void onNothingSelected(AdapterView<?> parent)
    {

    }



}
