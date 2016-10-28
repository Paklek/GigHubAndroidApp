package com.gighub.app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.gighub.app.R;
import com.gighub.app.model.MusicianGenreModel;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.activity.MusicianActivity;
import com.gighub.app.ui.activity.SearchResultActivity;
import com.gighub.app.util.CloudinaryUrl;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Paklek on 6/12/2016.
 */
public class ListSearchResultAdapter extends BaseAdapter {
    Context mContext;
    private List<MusicianModel> mListMusician;
    private List<SearchResultModel> mSearchResultModel;

    private static LayoutInflater inflater=null;

    public ListSearchResultAdapter(Context mContext, List<SearchResultModel> mSearchResultModel)
    {
//       this.mListMusician = mListMusician;
        this.mSearchResultModel = mSearchResultModel;
        this.mContext = mContext;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mSearchResultModel.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView mTextViewName, mTextViewHarga, mTextViewMusicianGenres, mTextViewDeskripsi;
        CircularImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();

        final View rowView;
//        Cloudinary cloudinary = new Cloudinary();
        rowView = inflater.inflate(R.layout.lv_musician_result,null);
        holder.mTextViewName=(TextView) rowView.findViewById(R.id.musician_name);
        holder.mTextViewHarga = (TextView)rowView.findViewById(R.id.musician_fee);
        holder.img=(CircularImageView) rowView.findViewById(R.id.img_musicianresult);
        holder.mTextViewMusicianGenres = (TextView)rowView.findViewById(R.id.musician_genres);
//        holder.mTextViewDeskripsi = (TextView)rowView.findViewById(R.id.musician_genres);

//        Map config = new HashMap();
//        config.put("cloud_name", "dv5anayv1");
//        config.put("api_key", "627999879788938");
//        config.put("api_secret", "Tfnf61ZtXn61Ct-Jbr_F__lzSJ8@dv5anayv1");
//        Cloudinary cloudinary = new Cloudinary(config);

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        if(mSearchResultModel.get(position).getPhoto()!=null && !mSearchResultModel.get(position).getPhoto().equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mSearchResultModel.get(position).getPhoto())).into(holder.img);
        }

//        cloudinary.url().type("search").format("jpg").generate(mSearchResultModel.get(position).getPhoto());

        holder.mTextViewName.setText(mSearchResultModel.get(position).getName());
//        holder.mTextViewName.setText(mListMusicianGenre.get(position).getMusician().getName());
        holder.mTextViewHarga.setText("Rp."+mSearchResultModel.get(position).getHarga_sewa()+"/Hour");
        holder.mTextViewMusicianGenres.setText(mSearchResultModel.get(position).getGenrenya());
//        holder.img.setImage
//        holder.mTextViewHarga.setText(mListMusicianGenre.get(position).getMusician().getHarga_sewa());
//        String g ="";
//        for(int i =0;i<mSearchResultModel.size();i++){
//
//            for (int j =0;j<i;j++) {
//                if (mSearchResultModel.get(getViewTypeCount()).getId() > 1) {
//                    g += " " + mSearchResultModel.get(position).getGenre_name();
//                    holder.mTextViewMusicianGenres.setText(""+g);
//                }
//            }
//            g="";
//        }


//        holder.img.setImageResource(mListMusician.get(position).getPhoto());
//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(mContext, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
//                Intent i = new Intent(mContext, MusicianActivity.class);
//                rowView.getContext().startActivity(i);
//
//            }
//        });
        return rowView;
    }
}
