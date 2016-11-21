package com.gighub.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.DataObject;
import com.gighub.app.model.Gig;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.UserModel;
import com.gighub.app.ui.activity.GigActivity;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paklek on 6/19/2016.
 */
public class ListDiscoverGigAdapter extends RecyclerView.Adapter<ListDiscoverGigAdapter.DataObjectHolder>
{

    private static String LOG_TAG = "ListDiscoverGigAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
//    private List<MusicianModel> mDaftarMusician;
    private List<Gig> mGig;
    private int mPos;
    public static Context mContext;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label_name, musicianDate, mTextViewNamaGig;
        ImageView mImageViewImageGig;
        GridLayout mGridLayout;
        FrameLayout frameGig;
        String mNamaGig,mImgGig;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mImageViewImageGig = (ImageView)itemView.findViewById(R.id.img_img_gig_discovergig);
            mTextViewNamaGig = (TextView)itemView.findViewById(R.id.tv_namagig_discovergig);
            label_name = (TextView) itemView.findViewById(R.id.musician_name);
            musicianDate = (TextView) itemView.findViewById(R.id.musician_gig_date);
            mGridLayout = (GridLayout)itemView.findViewById(R.id.gridlayout_gig_discovergig);
            frameGig = (FrameLayout)itemView.findViewById(R.id.frame_gig);
            Log.i(LOG_TAG, "Adding Listener");
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

//    public ListDiscoverGigAdapter(List<MusicianModel> data) {
//        mDaftarMusician = data;
//    }

    public ListDiscoverGigAdapter(List<Gig> data){
        mGig = data;
    }

    public List<Gig> getmGig() {
        return mGig;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cv_discover_gig, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        if(mGig.get(position).getPhoto_gig()!=null && !mGig.get(position).getPhoto_gig().equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mGig.get(position).getPhoto_gig())).into(holder.mImageViewImageGig);
        }
//        holder.mImageViewImageGig.
        holder.mNamaGig = mGig.get(position).getNama_gig();
        mPos=position;

        holder.mTextViewNamaGig.setText(holder.mNamaGig);
        holder.label_name.setText(mGig.get(position).getLokasi());
        holder.musicianDate.setText(mGig.get(position).getTanggal_mulai());
        holder.frameGig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GigActivity.class);
                intent.putExtra("gig_id",mGig.get(position).getId());
                intent.putExtra("nama_gig",holder.mNamaGig);
                intent.putExtra("photo_gig",mGig.get(mPos).getPhoto_gig());
                intent.putExtra("gig_id",mGig.get(position).getId());
                intent.putExtra("tanggal_mulai",mGig.get(position).getTanggal_mulai());
                intent.putExtra("tanggal_selesai",mGig.get(position).getTanggal_selesai());
                intent.putExtra("deskripsi",mGig.get(position).getDeskripsi());
                intent.putExtra("lokasi",mGig.get(position).getLokasi());
                intent.putExtra("lokasi_detail",mGig.get(position).getDetail_lokasi());
                intent.putExtra("lat",mGig.get(position).getLat());
                intent.putExtra("lng",mGig.get(position).getLng());
                intent.putExtra("user_id",mGig.get(position).getUser_id());

                Log.d("photo",mGig.get(mPos).getPhoto_gig());

                mContext.startActivity(intent);
            }
        });

        holder.mGridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GigActivity.class);
                intent.putExtra("nama_gig",holder.mNamaGig);
                intent.putExtra("gig_id",mGig.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }
//
//    public void addItem(DataObject dataObj, int index) {
////        mDataset.add(index, dataObj);
//        notifyItemInserted(index);
//    }
//
//    public void deleteItem(int index) {
//        mDataset.remove(index);
//        notifyItemRemoved(index);
//    }

    @Override
    public int getItemCount() {
        return mGig.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
