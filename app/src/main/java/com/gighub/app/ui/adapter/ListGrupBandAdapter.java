package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.GroupBand;

import java.util.List;

/**
 * Created by user on 02/10/2016.
 */
public class ListGrupBandAdapter extends BaseAdapter {

    Context mContext;
    private List<GroupBand> mGroupBand;

    private static LayoutInflater inflater=null;

    public ListGrupBandAdapter(Context mContext, List<GroupBand> mGroupBand){
        this.mGroupBand = mGroupBand;
        this.mContext = mContext;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
//        int tmp=0;
//        if ()
        return mGroupBand.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();

        String mHarga;

        mHarga = Integer.toString(mGroupBand.get(position).getHarga());
        final View rowView;
        rowView = inflater.inflate(R.layout.cv_grup_band,null);
        holder.mTextViewNamaBand = (TextView)rowView.findViewById(R.id.tv_nama_grupband_grupband);
        holder.mTextViewHargaBand = (TextView)rowView.findViewById(R.id.tv_harga_grupband);
        holder.mTextViewKota = (TextView)rowView.findViewById(R.id.tv_kota_grupband);

        holder.mTextViewNamaBand.setText(mGroupBand.get(position).getNama_grupband());
        holder.mTextViewKota.setText(mGroupBand.get(position).getKota());
        holder.mTextViewHargaBand.setText(mHarga);

        return rowView;
    }

    public class Holder{
        TextView mTextViewNamaBand, mTextViewHargaBand, mTextViewKota;

    }
}
