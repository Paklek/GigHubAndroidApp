package com.gighub.app.ui.adapter;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by user on 05/09/2016.
 */
public class ListGenreAdapter extends RecyclerView.Adapter<ListGenreAdapter.ListGenreViewHolder> {
    private List<Genre> mListGenre;

    public ListGenreAdapter(List<Genre> genres){
        this.mListGenre = genres;
        Log.d("adapter","Aku dibuat");
        Log.d("adapter",new Gson().toJson(genres));
    }

    @Override
    public ListGenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_genre,parent,false);

        Log.d("adapter","Aku dipanggil");
        return new ListGenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListGenreViewHolder holder, final int position) {
        holder.cbxGenre.setChecked(mListGenre.get(position).getSelected());
        holder.tvGenre.setText(mListGenre.get(position).getGenre_name());

        Log.d("adapter","Aku dilihat");
        holder.cbxGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListGenre.get(position).getSelected()) {
                    mListGenre.get(position).setSelected(false);
                }
                else {
                    mListGenre.get(position).setSelected(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        Log.d("adapter","Aku dihitung");
        return mListGenre.size();

    }

    public static  class ListGenreViewHolder extends RecyclerView.ViewHolder{

        public TextView tvGenre;
        public CheckBox cbxGenre;
        public ListGenreViewHolder(View itemView) {
            super(itemView);

            Log.d("adapter","Aku dilengketin");
            tvGenre = (TextView)itemView.findViewById(R.id.tv_genre);
            cbxGenre = (CheckBox)itemView.findViewById(R.id.cbx_genre);
        }
    }

}
