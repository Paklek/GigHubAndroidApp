package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Penyewaan;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

/**
 * Created by user on 24/10/2016.
 */
public class ListOnProccessBookingAdapter extends BaseAdapter {
    private Context mContext;
    private List<Penyewaan> mPenyewaan;

    private static LayoutInflater inflater=null;

    public ListOnProccessBookingAdapter(Context mContext, List<Penyewaan> mPenyewaan){
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
        TextView mTextViewName, mTextViewGenre, mTextViewHour, mTextViewVerify;
        CircularImageView mImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.lv_onrequestbook,null);
        holder.mTextViewName = (TextView)rowView.findViewById(R.id.tv_musician_name_onproccess);
        holder.mTextViewGenre = (TextView)rowView.findViewById(R.id.tv_musician_genres_onproccess);
        holder.mTextViewHour = (TextView)rowView.findViewById(R.id.tv_musician_hour_fee_onproccess);
        holder.mTextViewVerify = (TextView)rowView.findViewById(R.id.tv_verify_onproccess);

//        if(mPenyewaan.get(position).getTipe()!=null){
        holder.mTextViewName.setText(mPenyewaan.get(position).getFirst_name());
        holder.mTextViewGenre.setText(mPenyewaan.get(position).getNama_gig());
        holder.mTextViewHour.setText("Rp. "+mPenyewaan.get(position).getTotal_biaya());
        if (mPenyewaan.get(position).getStatus().equals("1")){
            holder.mTextViewVerify.setText("Not Verified");
            holder.mTextViewVerify.setTextColor(holder.mTextViewVerify.getResources().getColor(R.color.colorPrimary));
        }
        else if (mPenyewaan.get(position).getStatus().equals("2")){
            holder.mTextViewVerify.setText("Verified");
            holder.mTextViewVerify.setTextColor(holder.mTextViewVerify.getResources().getColor(R.color.primaryButton2));
        }

        return rowView;
    }
}
