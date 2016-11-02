package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.GigOfferMusician;
import com.gighub.app.util.CloudinaryUrl;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 01/11/2016.
 */
public class ListGigOfferMusicianAdapter extends BaseAdapter {

    private List<GigOfferMusician> mGigOfferMusicianList;
    private Context mContext;
    private static LayoutInflater inflater=null;

    public ListGigOfferMusicianAdapter(Context mContext, List<GigOfferMusician> mGigOfferMusicianList){
        this.mGigOfferMusicianList = mGigOfferMusicianList;
        this.mContext = mContext;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mGigOfferMusicianList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return mGigOfferMusicianList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rowView;
        rowView = inflater.inflate(R.layout.lv_musician_result,null);

        Holder holder = new Holder();

        holder.mTextViewName=(TextView) rowView.findViewById(R.id.musician_name);
        holder.mTextViewHarga = (TextView)rowView.findViewById(R.id.musician_fee);
        holder.img=(CircularImageView) rowView.findViewById(R.id.img_musicianresult);
        holder.mTextViewMusicianGenres = (TextView)rowView.findViewById(R.id.musician_genres);

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        if(mGigOfferMusicianList.get(position).getPhoto()!=null && !mGigOfferMusicianList.get(position).getPhoto().equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mGigOfferMusicianList.get(position).getPhoto())).into(holder.img);
        }

//        cloudinary.url().type("search").format("jpg").generate(mSearchResultModel.get(position).getPhoto());

        holder.mTextViewName.setText(mGigOfferMusicianList.get(position).getName());
//        holder.mTextViewName.setText(mListMusicianGenre.get(position).getMusician().getName());
        holder.mTextViewHarga.setText("Rp."+mGigOfferMusicianList.get(position).getHarga_sewa()+"/Hour");
        holder.mTextViewMusicianGenres.setText(mGigOfferMusicianList.get(position).getGenrenya());
//

        return rowView;
    }
    public class Holder{
        TextView mTextViewName, mTextViewHarga, mTextViewMusicianGenres;
        ImageView img;

    }
}
