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

import com.gighub.app.R;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.ui.activity.MusicianActivity;
import com.gighub.app.ui.activity.SearchResultActivity;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paklek on 6/12/2016.
 */
public class ListSearchResultAdapter extends BaseAdapter {
    Context context;
    private List<MusicianModel> mListMusician;

    private static LayoutInflater inflater=null;

    public ListSearchResultAdapter(Context context, List<MusicianModel> mListMusician)
    {
       this.mListMusician = mListMusician;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mListMusician.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView mTextViewName, mTextViewHarga;
        CircularImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.lv_musician_result,null);
        holder.mTextViewName=(TextView) rowView.findViewById(R.id.musician_name);
        holder.mTextViewHarga = (TextView)rowView.findViewById(R.id.musician_fee);
        holder.img=(CircularImageView) rowView.findViewById(R.id.img1);
        holder.mTextViewName.setText(mListMusician.get(position).getName());
        holder.mTextViewHarga.setText(mListMusician.get(position).getHarga_sewa());
//        holder.img.setImageResource(imageId[position]);
//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
//                Intent i = new Intent(context, MusicianActivity.class);
//                rowView.getContext().startActivity(i);
//
//            }
//        });
        return rowView;
    }
}
