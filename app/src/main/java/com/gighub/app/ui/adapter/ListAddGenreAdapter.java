package com.gighub.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.MusicianGenresResponse;
import com.gighub.app.model.ResponseCallGenre;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
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
 * Created by user on 02/11/2016.
 */
public class ListAddGenreAdapter extends BaseAdapter {

    private List<Genre> mGenreList;
    private Context mContext;
    private static LayoutInflater inflater=null;
    private SessionManager mSession;
    private List<Genre> mMusicianGenres;
    private SparseBooleanArray mCheckedStates;

    public ListAddGenreAdapter(Context mContext, List<Genre> mGenreList, List<Genre> mMusicianGenres){
        this.mGenreList = mGenreList;
        this.mMusicianGenres = mMusicianGenres;
        this.mContext = mContext;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        mCheckedStates = new SparseBooleanArray(mGenreList.size());
    }

    @Override
    public int getCount() {
        return mGenreList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return mGenreList.get(position).getId();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        mSession = new SessionManager(mContext);
//        mMusicianGenres = new ArrayList<Genre>();
        final  View rowView;
        rowView = inflater.inflate(R.layout.cv_genre_horizontal,null);


        final Holder holder = new Holder();
        holder.mCheckBoxGenre = (TextView) rowView.findViewById(R.id.cbx_genre);
        holder.mLinearLayout = (LinearLayout)rowView.findViewById(R.id.ll_cv_genre_horizontal);

        holder.mCheckBoxGenre.setText(mGenreList.get(position).getGenre_name());
//        holder.mCheckBoxGenre.setBackgroundDrawable(holder.mCheckBoxGenre.getResources().getDrawable(R.drawable.spinner_drawable));
        holder.mCheckBoxGenre.setTextColor(holder.mCheckBoxGenre.getResources().getColor(R.color.colorTextDark));

        for(int i=0;i<mMusicianGenres.size();i++) {
            if (mMusicianGenres.get(i).getId() == mGenreList.get(position).getId()) {
                holder.mLinearLayout.setBackground(holder.mLinearLayout.getResources().getDrawable(R.drawable.button_border2));
                mGenreList.get(position).setSelected(true);
            }
        }




        return rowView;

    }


    public static class Holder{
        TextView mCheckBoxGenre;
        LinearLayout mLinearLayout;
        Map<String, String> musicianGenreData = new HashMap<>();
//        Intent intent = new Intent(mContext)
    }

}
