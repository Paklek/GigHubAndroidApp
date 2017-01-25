package com.gighub.app.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.activity.BookMusicianActivity;
import com.gighub.app.ui.activity.YouTubeVideoActivity;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.YoutubeVideoConfig;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicianProfileFragment extends Fragment {

    Context mContext;
    private String mDescriptions, mGenre, mName,mHarga, mTipe, mYouTubeVideoUrl;
    private int pos=0, mId;
    private TextView mTextViewMusicianDescriptions, mTextViewMusicianGenres, mTextViewTipeMusisi, mTextViewAnggota;
    private Button mButtonBookRequest, mButtonYouTubeVideo;
    private YouTubePlayerView mYouTubeView;

    private View mViewButtonBookRequest;

    private SessionManager mSession;

    private List<SearchResultModel> mSearchResult;

    public MusicianProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_musician_profile, container, false);

        mContext = inflater.getContext();
        mSession = new SessionManager(getActivity().getApplicationContext());

        mViewButtonBookRequest = (View)view.findViewById(R.id.btn_book_request_musicianprofile);
        mTextViewAnggota = (TextView)view.findViewById(R.id.tv_anggota_musicianprofile);
//        mYouTubeView = (YouTubePlayerView)view.findViewById(R.id.youtube_view);
        mButtonYouTubeVideo = (Button)view.findViewById(R.id.btn_youtubevideo_musicianprofile);
//        mYouTubeView.initialize(YoutubeVideoConfig.YOUTUBE_API_KEYz );

        if(mSession.isLoggedIn()){
            if(mSession.checkUserType().equals("org")){
//                mName = mSession.getUserDetails().getFirst_name();
            }
            else if(mSession.checkUserType().equals("msc")){
                mName = mSession.getMusicianDetails().getName();
                mViewButtonBookRequest.setVisibility(mViewButtonBookRequest.GONE);
//                    mMusicianId = mSession.getMusicianDetails().getId();
//                    mGenre = mSession.getMusicianDetails().getGenreMusician().get(0).getGenres().getGenre_name();
//                    mGenre = mSession.getMusicians().getGenreMusician().get(mSession.getGenreMusician().getMusician_id()).getGenres().getGenre_name();
//                    mName = mSession.getGenreMusician().getMusicians().getName();
//                    mGenre = mSession.getMusicians().getName();
//                    mGenre = mSession.getMusicians().getGenreMusician().get(mSession.getMusicians().getId()).getGenres().getGenre_name();
            }
//            mToolbar.setTitle("GigHub - "+mName);
        }




        mTextViewMusicianDescriptions = (TextView)view.findViewById(R.id.tv_musician_descriptions);
        mTextViewMusicianGenres = (TextView)view.findViewById(R.id.tv_musician_genres);
        mTextViewTipeMusisi = (TextView)view.findViewById(R.id.tv_tipe_musisi);

        mButtonBookRequest = (Button)view.findViewById(R.id.btn_book_request_musicianprofile);

        Intent intent = getActivity().getIntent();


        final Type type = new TypeToken<List<SearchResultModel>>(){}.getType();
        mSearchResult = new Gson().fromJson(intent.getStringExtra("search"),type);


        mDescriptions = intent.getStringExtra("deskripsi");
        mGenre = intent.getStringExtra("genre");
        pos = intent.getIntExtra("posisi",pos);
        mName = intent.getStringExtra("name");
        mHarga = intent.getStringExtra("harga_sewa");
        mTipe = intent.getStringExtra("tipe");
        mId = intent.getIntExtra("id",0);
        mYouTubeVideoUrl = intent.getStringExtra("youtube_video");
        Log.d("pos",""+pos+" fragment");

        if(mTipe.equals("Solo")){
            mTextViewAnggota.setVisibility(View.GONE);
        }
        else if(mTipe.equals("Group")){
            mTextViewAnggota.setVisibility(View.VISIBLE);
        }

        mTextViewMusicianDescriptions.setText(": "+mDescriptions);
        mTextViewMusicianGenres.setText(": "+ mGenre);
        mTextViewTipeMusisi.setText(": "+mTipe);


        mButtonYouTubeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), YouTubeVideoActivity.class);
                intent1.putExtra("youtube_video", mYouTubeVideoUrl);
                startActivity(intent1);
            }
        });

        mButtonBookRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mSession.isLoggedIn()){
                    Toast.makeText(mContext, R.string.you_must_be_logged,Toast.LENGTH_LONG).show();
                }
                else {

                    Log.d("pos", "" + pos + " fragmentclick");
                    Intent intent1 = new Intent(getActivity().getApplicationContext(), BookMusicianActivity.class);
                    intent1.putExtra("id", mId);
                    intent1.putExtra("pos", pos);
                    intent1.putExtra("name", mName);
                    intent1.putExtra("genre", mGenre);
                    intent1.putExtra("harga_sewa", mHarga);
                    intent1.putExtra("tipe", mTipe);
//                intent1.putExtra("harga_sewa",mSearchResult.get(pos).getHarga_sewa());

                    startActivity(intent1);
                }
            }
        });




        return view;

    }

}
