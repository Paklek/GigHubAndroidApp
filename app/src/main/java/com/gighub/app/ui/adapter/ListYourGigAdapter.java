package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Gig;
import com.gighub.app.model.YourGig;
import com.gighub.app.util.CloudinaryUrl;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 08/10/2016.
 */
public class ListYourGigAdapter extends BaseAdapter{
    Context mContext;
    private List<YourGig> mListYourGig;

    private static LayoutInflater inflater = null;

    public ListYourGigAdapter(Context mContext, List<YourGig> mListYourGig){
        this.mContext = mContext;
        this.mListYourGig = mListYourGig;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mListYourGig.size();
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
        TextView mTextViewNamaGig, mTextViewLocation, mTextViewDescriptions, mTextViewTanggalMulai;
        ImageView mImageViewGigPhoto;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        final View rowView;

        rowView = inflater.inflate(R.layout.cv_yourgig,null);

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
//        mPos = holder.getAdapterPosition();
//        final Gig item = mGig.get(position);
//

        holder.mTextViewNamaGig = (TextView)rowView.findViewById(R.id.tv_nama_gig_yourgig);
        holder.mTextViewLocation = (TextView)rowView.findViewById(R.id.tv_location_gig_yourgig);
        holder.mTextViewTanggalMulai = (TextView)rowView.findViewById(R.id.tv_tanggal_mulai_yourgig);
        holder.mTextViewDescriptions = (TextView)rowView.findViewById(R.id.tv_deskripsi_yourgig);
        holder.mImageViewGigPhoto = (ImageView)rowView.findViewById(R.id.btn_img_cover_gig_yourgig);

        if(!mListYourGig.get(position).getPhoto_gig().equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mListYourGig.get(position).getPhoto_gig())).into(holder.mImageViewGigPhoto);
        }

        holder.mTextViewNamaGig.setText(mListYourGig.get(position).getNama_gig());
        holder.mTextViewLocation.setText(mListYourGig.get(position).getLokasi());
        holder.mTextViewTanggalMulai.setText(mListYourGig.get(position).getTanggal_mulai());
        holder.mTextViewDescriptions.setText(mListYourGig.get(position).getDeskripsi());

        return rowView ;
    }
}
