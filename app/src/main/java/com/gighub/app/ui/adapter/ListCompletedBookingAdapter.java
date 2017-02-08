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
 * Created by user on 26/10/2016.
 */
public class ListCompletedBookingAdapter extends BaseAdapter {

    private Context mContext;
    private List<Penyewaan> mPenyewaan;
    private String mStatus, mStatusRequest;
    private SessionManager mSession;

    private static LayoutInflater inflater=null;

    public ListCompletedBookingAdapter(Context mContext, List<Penyewaan> mPenyewaan){
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
        return mPenyewaan.get(position).getId();
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
        rowView = inflater.inflate(R.layout.lv_completedbook,null);
        holder.mTextViewName = (TextView)rowView.findViewById(R.id.tv_musician_name_completedbook);
        holder.mTextViewGenre = (TextView)rowView.findViewById(R.id.tv_musician_genres_completedbook);
        holder.mTextViewHour = (TextView)rowView.findViewById(R.id.tv_musician_hour_fee_completedbook);
        holder.mTextViewVerify = (TextView)rowView.findViewById(R.id.tv_status_completedbook);

        mStatus = ""+mPenyewaan.get(position).getStatus();
        mStatusRequest = ""+mPenyewaan.get(position).getStatus_request();

        holder.mTextViewName.setText(mPenyewaan.get(position).getFirst_name()+" "+mPenyewaan.get(position).getLast_name());
        holder.mTextViewGenre.setText(mPenyewaan.get(position).getNama_gig());
        holder.mTextViewHour.setText("Rp. "+mPenyewaan.get(position).getTotal_biaya());

        if (mStatus.equals("3") && mStatusRequest.equals("1")){
            holder.mTextViewVerify.setText("Done | wait to review");
            holder.mTextViewVerify.setTextColor(holder.mTextViewVerify.getResources().getColor(R.color.primaryOrange2));
        }
        else if (mStatus.equals("4") && mStatusRequest.equals("1")){
            holder.mTextViewVerify.setText("Done");
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
