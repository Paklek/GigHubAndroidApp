package com.gighub.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.DataObject;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.UserModel;
import com.gighub.app.ui.activity.GigActivity;

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
    private List<MusicianModel> mDaftarMusician;
    public static Context mContext;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label_name;
        TextView musicianDate;
        FrameLayout frameGig;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label_name = (TextView) itemView.findViewById(R.id.musician_name);
            musicianDate = (TextView) itemView.findViewById(R.id.musician_gig_date);
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

    public ListDiscoverGigAdapter(List<MusicianModel> data) {
        mDaftarMusician = data;
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
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label_name.setText(mDaftarMusician.get(position).getName());
        holder.musicianDate.setText(mDaftarMusician.get(position).getDescriptions());
        holder.frameGig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GigActivity.class);
//                intent.putExtra("user_id",123);
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
        return mDaftarMusician.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
