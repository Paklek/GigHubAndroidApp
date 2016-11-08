package com.gighub.app.ui.adapter;

import android.content.Context;
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
 * Created by user on 24/10/2016.
 */
public class ListOnProccessBookingAdapter extends BaseAdapter {
    private Context mContext;
    private List<Penyewaan> mPenyewaan;
    private String mStatus, mStatusRequest;
    private SessionManager mSession;

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
//        Holder holder =new Holder();
//        for(position=0;position<mPenyewaan.size();position++) {
//            if (mPenyewaan.get(position).getStatus().equals("1")) {
//                holder.mTextViewVerify.setText("Not Verified");
//            } else if(mPenyewaan.get(position).getStatus().equals("2")) {
//                holder.mTextViewVerify.setText("Verified");
//
//            }
//        }

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

        mSession = new SessionManager(mContext.getApplicationContext());

        final View rowView;
        rowView = inflater.inflate(R.layout.lv_on_proccess,null);
        holder.mTextViewName = (TextView)rowView.findViewById(R.id.tv_musician_name_onproccess);
        holder.mTextViewGenre = (TextView)rowView.findViewById(R.id.tv_musician_genres_onproccess);
        holder.mTextViewHour = (TextView)rowView.findViewById(R.id.tv_musician_hour_fee_onproccess);
        holder.mTextViewVerify = (TextView)rowView.findViewById(R.id.tv_verify_onproccess);

        mStatus = ""+mPenyewaan.get(position).getStatus();
        mStatusRequest = ""+mPenyewaan.get(position).getStatus_request();

//        if(mPenyewaan.get(position).getTipe()!=null){
        holder.mTextViewName.setText(mPenyewaan.get(position).getFirst_name());
        holder.mTextViewGenre.setText(mPenyewaan.get(position).getNama_gig());
        holder.mTextViewHour.setText("Rp. "+mPenyewaan.get(position).getTotal_biaya());
        if (mStatus.equals("0" )&& mStatusRequest.equals("1")){
            holder.mTextViewVerify.setText("Waiting to Paid");
            holder.mTextViewVerify.setTextColor(holder.mTextViewVerify.getResources().getColor(R.color.colorPrimary));
        }
        else if (mStatus.equals("1") && mStatusRequest.equals("1")){
            holder.mTextViewVerify.setText("Waiting to Verified");
            holder.mTextViewVerify.setTextColor(holder.mTextViewVerify.getResources().getColor(R.color.primaryButton2));
        }
        else if (mStatus.equals("2") && mStatusRequest.equals("1")){
            holder.mTextViewVerify.setText("Verified");
            holder.mTextViewVerify.setTextColor(holder.mTextViewVerify.getResources().getColor(R.color.green));
        }

        if(mSession.checkUserType().equals("org")){
            holder.mTextViewName.setText(mPenyewaan.get(position).getName());
        }
        else if(mSession.checkUserType().equals("msc")){
            holder.mTextViewName.setText(mPenyewaan.get(position).getFirst_name()+" "+mPenyewaan.get(position).getLast_name());
        }

        return rowView;
    }
}
