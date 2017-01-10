package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 30/12/2016.
 */
public class ListGenreCreateBandAdapter extends BaseAdapter {

    private List<Genre> mGenreList;
    private Context mContext;
    private static LayoutInflater inflater=null;

    public ListGenreCreateBandAdapter(Context mContext, List<Genre> mGenreList){
        this.mGenreList = mGenreList;
        this.mContext = mContext;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final  View rowView;
        rowView = inflater.inflate(R.layout.cv_genre_horizontal,null);
        final Holder holder = new Holder();
        holder.mCheckBoxGenre = (TextView) rowView.findViewById(R.id.cbx_genre);
        holder.mLinearLayout = (LinearLayout)rowView.findViewById(R.id.ll_cv_genre_horizontal);

        holder.mCheckBoxGenre.setText(mGenreList.get(position).getGenre_name());
//        holder.mCheckBoxGenre.setBackgroundDrawable(holder.mCheckBoxGenre.getResources().getDrawable(R.drawable.spinner_drawable));
        holder.mCheckBoxGenre.setTextColor(holder.mCheckBoxGenre.getResources().getColor(R.color.colorTextDark));
        holder.mLinearLayout.setBackground(holder.mLinearLayout.getResources().getDrawable(R.drawable.spinner_drawable));
        mGenreList.get(position).setSelected(false);

//        for(int i=0;i<mGenreList.size();i++) {
//            if (mGenreList.get(i).getId() == mGenreList.get(position).getId()) {
//                holder.mLinearLayout.setBackground(holder.mLinearLayout.getResources().getDrawable(R.drawable.button_border_black));
//                mGenreList.get(position).setSelected(true);
//            }
//        }


        return rowView;
    }

    public static class Holder{
        TextView mCheckBoxGenre;
        LinearLayout mLinearLayout;
        Map<String, String> genreData = new HashMap<>();
//        Intent intent = new Intent(mContext)
    }
}
