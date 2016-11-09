package com.gighub.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Penyewaan;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 19/10/2016.
 */
public class ListOnRequestBookingAdapter extends BaseAdapter {

    private Context mContext;
    private List<Penyewaan> mPenyewaan;
    private String mStatusBook;
    private SessionManager mSession;


    private static LayoutInflater inflater=null;

//    public ListOnRequestBookingAdapter(List<Penyewaan> data){
//        mPenyewaan = data;
//    }

    public ListOnRequestBookingAdapter(Context mContext, List<Penyewaan> mPenyewaan){
        this.mPenyewaan = mPenyewaan;
        this.mContext = mContext;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount()
    {
        return mPenyewaan.size();
//        int count=0;
//        for(int i=0;i<mPenyewaan.size();i++){
//            if (mPenyewaan.get(i).getStatus().equals("0")) {
//                count=i;
////                return i;
//            }
//        }
//        return count;
    }

    @Override
    public Object getItem(int position) {
//        for(position=0;position<mPenyewaan.size();position++) {
//            if (mPenyewaan.get(position).getStatus().equals("1")) {
//                return null;
//            } else {
//                return position;
//            }
//        }
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView mTextViewName, mTextViewGenre, mTextViewHour, mTextViewStatusBook;
        CircularImageView mImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListOnRequestBookingAdapter adapter = new ListOnRequestBookingAdapter(mContext,mPenyewaan);

        Holder holder = new Holder();
        mSession = new SessionManager(mContext.getApplicationContext());

        final View rowView;
        rowView = inflater.inflate(R.layout.lv_onrequestbook,null);
        holder.mTextViewName = (TextView)rowView.findViewById(R.id.tv_musician_name_onreqbook);
        holder.mTextViewGenre = (TextView)rowView.findViewById(R.id.tv_musician_genres_onreqbook);
        holder.mTextViewHour = (TextView)rowView.findViewById(R.id.tv_musician_hour_fee_onreqbook);
        holder.mImg = (CircularImageView) rowView.findViewById(R.id.img_onreqbook);

        holder.mTextViewStatusBook = (TextView)rowView.findViewById(R.id.tv_verify_onreqbook);

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();


//        create status request
        if(mPenyewaan.get(position).getType_sewa().equals("hiremusisi")){
            mStatusBook = "Book Musician | Waiting";
            holder.mTextViewStatusBook.setText(mStatusBook);
            holder.mTextViewStatusBook.setTextColor(holder.mTextViewStatusBook.getResources().getColor(R.color.primaryButton2));
        }

        else if(mPenyewaan.get(position).getType_sewa().equals("hireband")){
            mStatusBook = "Book Group | Waiting";
            holder.mTextViewStatusBook.setText(mStatusBook);
            holder.mTextViewStatusBook.setTextColor(holder.mTextViewStatusBook.getResources().getColor(R.color.primaryButton2));

        }
        else if(mPenyewaan.get(position).getType_sewa().equals("musisihire")){
            mStatusBook = "Gig Offer From Musician";
            holder.mTextViewStatusBook.setText(mStatusBook);
            holder.mTextViewStatusBook.setTextColor(holder.mTextViewStatusBook.getResources().getColor(R.color.primaryButton2));

        }
        else if(mPenyewaan.get(position).getType_sewa().equals("bandhire")){
            mStatusBook = "Gig Offer From Group";
            holder.mTextViewStatusBook.setText(mStatusBook);
            holder.mTextViewStatusBook.setTextColor(holder.mTextViewStatusBook.getResources().getColor(R.color.primaryButton2));
        }




        if(mSession.checkUserType().equals("org")){
            holder.mTextViewName.setText(mPenyewaan.get(position).getName());
            if(mPenyewaan.get(position).getPhoto()!=null && !mPenyewaan.get(position).getPhoto().equals("")) {
                Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPenyewaan.get(position).getPhoto())).into(holder.mImg);
            }
        }
        else if(mSession.checkUserType().equals("msc")){
            holder.mTextViewName.setText(mPenyewaan.get(position).getFirst_name()+" "+mPenyewaan.get(position).getLast_name());
            holder.mImg.setImageResource(android.R.drawable.ic_menu_gallery);
            if(mPenyewaan.get(position).getPhoto_gig()!=null && !mPenyewaan.get(position).getPhoto_gig().equals("")) {
                Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mPenyewaan.get(position).getPhoto_gig())).into(holder.mImg);
            }
        }

        holder.mTextViewGenre.setText(mPenyewaan.get(position).getNama_gig());
        holder.mTextViewHour.setText("Rp. " + mPenyewaan.get(position).getTotal_biaya());

        adapter.notifyDataSetChanged();
        return rowView;
    }
}
