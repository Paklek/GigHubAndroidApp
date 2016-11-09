package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.GroupBandMusisi;
import com.gighub.app.model.Position;
import com.gighub.app.util.SessionManager;

import java.util.List;

/**
 * Created by user on 09/11/2016.
 */
public class ListAddPositionMusicianAdapter extends BaseAdapter {

    private List<Position> mPositionList;
    private List<Position> mGroupBandMusisi;
    private Context mContext;
    private static LayoutInflater inflater=null;
    private SessionManager mSession;

    public ListAddPositionMusicianAdapter(Context mContext, List<Position> mPositionList){
        this.mContext = mContext;
        this.mPositionList = mPositionList;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mPositionList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return mPositionList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final  View rowView;
        final Holder holder = new Holder();
        mSession = new SessionManager(mContext);
        rowView = inflater.inflate(R.layout.lv_position,null);

        holder.mLinearLayoutPosition = (LinearLayout)rowView.findViewById(R.id.ll_list_position);
        holder.mTextViewPosition = (TextView)rowView.findViewById(R.id.tv_position);

        holder.mTextViewPosition.setText(mPositionList.get(position).getPosition_name());
        holder.mTextViewPosition.setTextColor(holder.mTextViewPosition.getResources().getColor(R.color.colorTextDark));

        return rowView;
    }

    public class Holder{
        LinearLayout mLinearLayoutPosition;
        TextView mTextViewPosition;
    }
}
