package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.YourBand;
import com.gighub.app.util.CloudinaryUrl;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 09/11/2016.
 */
public class ListAddMusicianToGroupAdapter extends BaseAdapter {


    private List<MusicianModel> mCalonAnggota;
    private Context mContext;

    private static LayoutInflater inflater = null;

    public ListAddMusicianToGroupAdapter(Context mContext, List<MusicianModel> mCalonAnggota){
        this.mContext = mContext;
        this.mCalonAnggota = mCalonAnggota ;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mCalonAnggota.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return mCalonAnggota.get(position).getId();
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
//        holder.img = (CircularImageView) rowView.findViewById(R.id.img_musicianresult_addmusiciantogroup);
        holder.mTextViewName.setText(mCalonAnggota.get(position).getName());
        holder.mTextViewMusicianGenres.setText(mCalonAnggota.get(position).getTipe()+" "+mCalonAnggota.get(position).getBasis());
        holder.mTextViewHarga.setText(mCalonAnggota.get(position).getHarga_sewa());

        if(mCalonAnggota.get(position).getPhoto()!=null && !mCalonAnggota.get(position).getPhoto().equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mCalonAnggota.get(position).getPhoto())).into(holder.img);
        }

        return rowView;
    }

    public class Holder{
        TextView mTextViewName, mTextViewHarga, mTextViewMusicianGenres, mTextViewDeskripsi;
        CircularImageView img;
    }
}
