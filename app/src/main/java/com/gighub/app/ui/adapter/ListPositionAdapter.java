package com.gighub.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.gighub.app.R;
import com.gighub.app.model.Position;
import com.gighub.app.ui.activity.CreateBandActivity;
import com.gighub.app.ui.activity.CreateGigActivity;
import com.gighub.app.ui.fragment.DialogPositionCreatBandFragment;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by user on 30/12/2016.
 */
public class ListPositionAdapter extends RecyclerView.Adapter<ListPositionAdapter.ListPositionViewHolder> {

    private List<Position> mPositions;
    private Context mContext;
    public final static int REQ_POSITION_CODE = 2000;

    private static LayoutInflater inflater=null;

    public ListPositionAdapter(List<Position> positions){
        this.mPositions = positions;
//        this.mContext = mContext;
//        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ListPositionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rb_position,parent,false);

        return new ListPositionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListPositionViewHolder holder, final int position) {

//        holder.mRadioButtonPosition.setText(mPositions.get(position).getPosition_name());

//        holder.mRadioButtonPosition.setChecked(false);
//        holder.mRadioButtonPosition.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //                    Log.d("position",""+mListPosition.get(i).getPosition_name()+" "+mListPosition.get(i).isSelected());
//                DialogPositionCreatBandFragment dialog = new DialogPositionCreatBandFragment();
//                Intent intent = new Intent(mContext, CreateGigActivity.class);
//                intent.putExtra("posisi",new Gson().toJson(mPositions));
//                Log.d("Data Log", new Gson().toJson(mPositions));
//                dialog.getTargetFragment().onActivityResult(dialog.getTargetRequestCode(), ListPositionAdapter.REQ_POSITION_CODE,intent);
//                dialog.dismiss();
//
////                getDialog().dismiss();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mPositions.size();
    }

    @Override
    public long getItemId(int position) {
        return mPositions.get(position).getId();
    }

    public static class ListPositionViewHolder extends RecyclerView.ViewHolder{

        public RadioButton mRadioButtonPosition;

        public ListPositionViewHolder(View itemView) {
            super(itemView);
//            mRadioButtonPosition = (RadioButton)itemView.findViewById(R.id.rb_position);
        }
    }

//    public void sendResult(int reqCode){
//        Intent intent = new Intent();
//        intent.putExtra("posisi",new Gson().toJson(mPositions));
//        Log.d("Data Log", new Gson().toJson(mPositions));
//        getTargetFragment().onActivityResult(getTargetRequestCode(),reqCode,intent);
//
//    }

}
