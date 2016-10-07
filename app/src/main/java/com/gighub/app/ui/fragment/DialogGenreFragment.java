package com.gighub.app.ui.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.ResponseCallGenre;
import com.gighub.app.model.RetrofitService;
import com.gighub.app.ui.activity.CreateBandActivity;
import com.gighub.app.ui.adapter.ListGenreAdapter;
import com.gighub.app.util.BuildUrl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogGenreFragment extends DialogFragment {

    private RecyclerView mRecyclerViewGenreDialog;

    private List<Genre> mListGenre;
    private Button mButtonSelection;
    private RecyclerView.LayoutManager mLayoutManager;

    private ListGenreAdapter mListGenreAdapter;

    public DialogGenreFragment() {
        // Required empty public constructor
    }

    static DialogGenreFragment newInstance(String isi){
        DialogGenreFragment dialogGenreFragment = new DialogGenreFragment();
        Bundle args = new Bundle();
        args.putString("isi",isi);
        dialogGenreFragment.setArguments(args);

        return dialogGenreFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_genre, container, false);
        // Inflate the layout for this fragment
        mButtonSelection = (Button)view.findViewById(R.id.btn_genre_select);
        mListGenre = new ArrayList<Genre>();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewGenreDialog = (RecyclerView)view.findViewById(R.id.rv_genre);

        mListGenreAdapter = new ListGenreAdapter(mListGenre);

        mRecyclerViewGenreDialog.setHasFixedSize(true);
        mRecyclerViewGenreDialog.setLayoutManager(mLayoutManager);
        mRecyclerViewGenreDialog.setAdapter(mListGenreAdapter);
        mRecyclerViewGenreDialog.getAdapter().notifyDataSetChanged();

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
        buildUrl.serviceGighub.loadMusicianGenre().enqueue(new Callback<ResponseCallGenre>() {
            @Override
            public void onResponse(Call<ResponseCallGenre> call, Response<ResponseCallGenre> response) {
                Log.d("data log",response.code()+ new Gson().toJson(response.body().getGenreList()));
                if(response.code()==200){
                    if(response.body().getError()==0){
                        mListGenre = response.body().getGenreList();
                        mListGenreAdapter = new ListGenreAdapter(mListGenre);

                        mRecyclerViewGenreDialog.setHasFixedSize(true);
                        mRecyclerViewGenreDialog.setLayoutManager(mLayoutManager);
                        mRecyclerViewGenreDialog.setAdapter(mListGenreAdapter);
                        mRecyclerViewGenreDialog.getAdapter().notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCallGenre> call, Throwable t) {

            }
        });


//        RetrofitService.getInstance().getApi().loadMusicianGenre().enqueue(new Callback<ResponseCallGenre>() {
//            @Override
//            public void onResponse(Call<ResponseCallGenre> call, Response<ResponseCallGenre> response) {
//                Log.d("data log",response.code()+ new Gson().toJson(response.body().getGenreList()));
//                if(response.code()==200){
//                    if(response.body().getError()==0){
//                        mListGenre = response.body().getGenreList();
//                        mListGenreAdapter = new ListGenreAdapter(mListGenre);
//
//                        mRecyclerViewGenreDialog.setHasFixedSize(true);
//                        mRecyclerViewGenreDialog.setLayoutManager(mLayoutManager);
//                        mRecyclerViewGenreDialog.setAdapter(mListGenreAdapter);
//                        mRecyclerViewGenreDialog.getAdapter().notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseCallGenre> call, Throwable t) {
//              //  Log.d("log",t.getMessage());
//            }
//        });

        mButtonSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(DiscoverMusicianFragment.REQCODE);

                getDialog().dismiss();
            }
        });

        return view;
    }

    void showDialog(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment dialogFragment = DialogGenreFragment.newInstance("isi");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_genre, new LinearLayout(getActivity()),false);

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);

        return builder;
    }

    public void sendResult(int reqCode){
        Intent intent = new Intent();
        intent.putExtra("kirim",new Gson().toJson(mListGenre));
        Log.d("Data Log", new Gson().toJson(mListGenre));
        getTargetFragment().onActivityResult(getTargetRequestCode(),reqCode,intent);
    }

//    public void sendResultActivity(int REQQODE){
//        Intent intent = new Intent();
//        intent.putExtra("kirim",new Gson().toJson(mListGenre));
//        getFragmentManager();
//    }
}
