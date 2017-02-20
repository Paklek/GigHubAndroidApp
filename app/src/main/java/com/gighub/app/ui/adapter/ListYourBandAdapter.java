package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.YourBand;
import com.gighub.app.model.YourGig;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 31/10/2016.
 */
public class ListYourBandAdapter extends BaseAdapter {

    private List<YourBand> mYourBandList;
    private Context mContext;
    private SessionManager mSession;

    private static LayoutInflater inflater = null;

    public ListYourBandAdapter(Context mContext, List<YourBand> mYourBandList){
        this.mContext = mContext;
        this.mYourBandList = mYourBandList ;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mYourBandList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return mYourBandList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        final View rowView;
        rowView = inflater.inflate(R.layout.cv_grup_band,null);
        mSession = new SessionManager(mContext.getApplicationContext());

        String mHarga;

        mHarga = Integer.toString(mYourBandList.get(position).getHarga());
        holder.mTextViewNamaBand = (TextView)rowView.findViewById(R.id.tv_nama_grupband_grupband);
        holder.mTextViewHargaBand = (TextView)rowView.findViewById(R.id.tv_harga_grupband);
        holder.mTextViewKota = (TextView)rowView.findViewById(R.id.tv_kota_grupband);
        holder.mTextViewTipe = (TextView)rowView.findViewById(R.id.tv_genre_grupband);
        holder.mImageBand = (ImageView)rowView.findViewById(R.id.btn_img_cover_band_grupband);
        holder.mImageMusician = (ImageView)rowView.findViewById(R.id.img_photo_musician_grupband);


        if(!mSession.getMusicianDetails().getPhoto().equals("") || !mYourBandList.get(position).getPhoto().equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mYourBandList.get(position).getPhoto())).into(holder.mImageMusician);
        }
        if(!mYourBandList.get(position).getCover().equals("") || mYourBandList.get(position).getCover()!=null) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mYourBandList.get(position).getCover())).into(holder.mImageBand);
        }
        holder.mTextViewNamaBand.setText(mYourBandList.get(position).getNama_grupband());
        holder.mTextViewKota.setText(mYourBandList.get(position).getKota());
        holder.mTextViewHargaBand.setText("Rp."+mHarga);
        holder.mTextViewTipe.setText(mYourBandList.get(position).getTipe());

        return rowView;
    }

    public class Holder{
        TextView mTextViewNamaBand, mTextViewHargaBand, mTextViewKota, mTextViewTipe;
        ImageView mImageBand, mImageMusician;

    }
}
