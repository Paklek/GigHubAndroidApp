package com.gighub.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Penyewaan;
import com.gighub.app.util.SessionManager;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

/**
 * Created by user on 19/10/2016.
 */
public class ListOnRequestBookingAdapter extends BaseAdapter {

    private Context mContext;
    private List<Penyewaan> mPenyewaan;

    private static LayoutInflater inflater=null;

//    public ListOnRequestBookingAdapter(List<Penyewaan> data){
//        mPenyewaan = data;
//    }

    public ListOnRequestBookingAdapter(Context mContext, List<Penyewaan> mPenyewaan){
        this.mPenyewaan = mPenyewaan;
        this.mContext = mContext;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mPenyewaan.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView mTextViewName, mTextViewGenre, mTextViewHour;
        CircularImageView mImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.lv_onrequestbook,null);
        holder.mTextViewName = (TextView)rowView.findViewById(R.id.tv_musician_name_onreqbook);
        holder.mTextViewGenre = (TextView)rowView.findViewById(R.id.tv_musician_genres_onreqbook);
        holder.mTextViewHour = (TextView)rowView.findViewById(R.id.tv_musician_hour_fee_onreqbook);


//        if(mPenyewaan.get(position).getTipe()!=null){
            holder.mTextViewName.setText(mPenyewaan.get(position).getFirst_name());
            holder.mTextViewGenre.setText(mPenyewaan.get(position).getNama_gig());
            holder.mTextViewHour.setText("Rp. "+mPenyewaan.get(position).getTotal_biaya());


        return rowView;
    }
}
