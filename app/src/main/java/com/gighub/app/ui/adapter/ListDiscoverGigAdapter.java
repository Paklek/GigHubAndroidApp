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
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.DataObject;
import com.gighub.app.model.DistanceGig;
import com.gighub.app.model.Gig;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.ResponseDistance;
import com.gighub.app.model.ResponseUser;
import com.gighub.app.model.UserModel;
import com.gighub.app.ui.activity.GigActivity;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private String myLat; // A poin 3
    private String myLng;//
    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label_name, musicianDate, mTextViewNamaGig, mTextViewDistance;
        ImageView mImageViewImageGig;
        GridLayout mGridLayout;
        FrameLayout frameGig;
        String mNamaGig,mImgGig;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mTextViewDistance = (TextView)itemView.findViewById(R.id.gig_distance_discovergig);
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
        myLat = null;
        myLng = null;
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
        mPos = holder.getAdapterPosition();
        final Gig item = mGig.get(position);
        if(!item.getPhoto_gig().equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(item.getPhoto_gig())).into(holder.mImageViewImageGig);
        }
//        holder.mImageViewImageGig.
        holder.mNamaGig = item.getNama_gig();
//        mPos=position;

        holder.mTextViewNamaGig.setText(holder.mNamaGig);
        holder.label_name.setText(item.getLokasi());
        holder.musicianDate.setText(item.getTanggal_mulai());
        holder.frameGig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mContext, GigActivity.class);
                intent.putExtra("gig_id",item.getId());
                intent.putExtra("nama_gig",holder.mNamaGig);
                intent.putExtra("photo_gig",mGig.get(mPos).getPhoto_gig());
                intent.putExtra("gig_id",item.getId());
                intent.putExtra("tanggal_mulai",item.getTanggal_mulai());
                intent.putExtra("tanggal_selesai",item.getTanggal_selesai());
                intent.putExtra("deskripsi",item.getDeskripsi());
                intent.putExtra("lokasi",item.getLokasi());
                intent.putExtra("lokasi_detail",item.getDetail_lokasi());
                intent.putExtra("lat",item.getLat());
                intent.putExtra("lng",item.getLng());
                intent.putExtra("user_id",item.getUser_id());

                Log.d("photo",mGig.get(mPos).getPhoto_gig());

                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();
                Map<String, String> dataProfile  = new HashMap<String, String>();
                dataProfile.put("user_id",Integer.toString(item.getUser_id()));
                buildUrl.serviceGighub.sendDataOrganizerProfile(dataProfile).enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if(response.code()==200) {
                            intent.putExtra("organizer", new Gson().toJson(response.body().getUser()));
                            mContext.startActivity(intent);
                        }
                        else {
                            Log.d("fail", "onResponse: "+response.code()+" "+response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        Log.d("fail", "onFailure: user organizer "+t.getMessage());
                    }
                });
            }
        });
        if(myLng!=null && myLat!=null && item.getLat()!=null && item.getLng()!=null){
            Log.d("Jarak",String.format("(my)%s %s,(item)%s %s",myLat,myLng,item.getLat(),item.getLng()));

            Log.d("Jarak","sudah di panggil");
            getDistance(holder,myLat,myLng,item.getLat(),item.getLng());
        }else{
            Log.d("Jarak","Belum di panggil");
        }
        holder.mGridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GigActivity.class);
                intent.putExtra("nama_gig",holder.mNamaGig);
                intent.putExtra("gig_id",item.getId());
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
    public void setMyLatLng(String lat,String lng){ // A point 2
        this.myLat=lat;
        this.myLng = lng;
        notifyDataSetChanged();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
    private void getDistance(final DataObjectHolder holder, String latAku, String lngAku, String latGig, String lngGig){

        String url = "https://maps.googleapis.com/maps/";
        Log.d("getDistance","ini ambil jarak");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        DistanceGig service = retrofit.create(DistanceGig.class);
        Log.d("Location",latAku + "," + lngAku+"-"+latGig + "," + lngGig);
        Call<ResponseDistance> call = service.getDistanceDuration("metric", latAku + "," + lngAku,latGig + "," + lngGig, "driving");
        call.enqueue(new Callback<ResponseDistance>() {
            @Override
            public void onResponse(Call<ResponseDistance> call, Response<ResponseDistance> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("Response", new Gson().toJson(response.body()));
                        if (response.body().getRoutes().size() > 0) {
                            Log.d("getDistance", "ini response :"+response.body().getRoutes());
                            Log.d("Jarak", response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText().toString());
                            updateView(holder, response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText().toString());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDistance> call, Throwable t) {

            }
        });
    }

    private void updateView(DataObjectHolder h, String s) {
        h.mTextViewDistance.setText(s);
    }
}
