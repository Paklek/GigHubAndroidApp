package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.util.CloudinaryUrl;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

/**
 * Created by user on 02/12/2016.
 */
public class ListViewRemoveAnggotaAdapter extends BaseAdapter {

    private List<MusicianModel> mCalonMantanAnggota;
    private Context mContext;

    private static LayoutInflater inflater = null;

    public ListViewRemoveAnggotaAdapter(Context mContext, List<MusicianModel> mCalonMantanAnggota){
        this.mContext = mContext;
        this.mCalonMantanAnggota = mCalonMantanAnggota;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mCalonMantanAnggota.size();
    }

    @Override
    public Object getItem(int position) {
        return mCalonMantanAnggota.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCalonMantanAnggota.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        final View rowView;
        rowView = inflater.inflate(R.layout.lv_musician_result,null);

        holder.mTextViewName=(TextView) rowView.findViewById(R.id.musician_name);
        holder.mTextViewHarga = (TextView)rowView.findViewById(R.id.musician_fee);
        holder.img=(CircularImageView) rowView.findViewById(R.id.img_musicianresult);
        holder.mTextViewMusicianGenres = (TextView)rowView.findViewById(R.id.musician_genres);

        holder.mTextViewName.setText(mCalonMantanAnggota.get(position).getName());
        holder.mTextViewMusicianGenres.setText(mCalonMantanAnggota.get(position).getTipe()+" "+mCalonMantanAnggota.get(position).getBasis());
        holder.mTextViewHarga.setText(mCalonMantanAnggota.get(position).getHarga_sewa());
        return rowView;
    }

    public class Holder{
        TextView mTextViewName, mTextViewHarga, mTextViewMusicianGenres, mTextViewDeskripsi;
        CircularImageView img;
    }
}
