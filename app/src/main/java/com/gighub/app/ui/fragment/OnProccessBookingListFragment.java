package com.gighub.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gighub.app.R;
import com.gighub.app.model.Penyewaan;
import com.gighub.app.ui.adapter.ListOnProccessBookingAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnProccessBookingListFragment extends Fragment {

    private List<Penyewaan> mPenyewaan;
    private ListView mListView;

    public OnProccessBookingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_on_proccess_book_list, container, false);

        mPenyewaan = new ArrayList<Penyewaan>();
        Intent i = getActivity().getIntent();

//        final Type type = new TypeToken<List<Penyewaan>>(){}.getType();
//        mPenyewaan = new Gson().fromJson(i.getStringExtra("onproc"),type);
//        mListView = (ListView)view.findViewById(R.id.lv_book_on_proc);
//        mListView.setAdapter(new ListOnProccessBookingAdapter(getActivity().getApplicationContext(),mPenyewaan));
//

        return view;
    }

}
