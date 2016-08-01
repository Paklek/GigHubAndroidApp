package com.gighub.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gighub.app.R;
import com.gighub.app.ui.activity.BookMusicianActivity;
import com.gighub.app.ui.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicianMusicFragment extends Fragment {


    public MusicianMusicFragment() {
        // Required empty public constructor
    }

    private Button btn_book;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_musician_music, container, false);
        btn_book = (Button)v.findViewById(R.id.btn_bookReq);

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookMusicianActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return v ;
    }

}
