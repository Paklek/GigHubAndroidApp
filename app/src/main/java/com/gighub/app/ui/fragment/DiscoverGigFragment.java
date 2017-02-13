package com.gighub.app.ui.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.DataObject;
import com.gighub.app.model.Gig;
import com.gighub.app.model.GigResponse;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.MusicianResponse;
import com.gighub.app.model.RetrofitService;
import com.gighub.app.model.ServiceGighub;
import com.gighub.app.model.UserModel;
import com.gighub.app.model.UserResponse;
import com.gighub.app.ui.activity.BookMusicianActivity;
import com.gighub.app.ui.activity.GigActivity;
import com.gighub.app.ui.adapter.ListDiscoverGigAdapter;
import com.gighub.app.util.BuildUrl;
import com.google.android.gms.maps.LocationSource;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverGigFragment extends Fragment implements LocationListener {

    private RecyclerView mRecyclerView;
    private ListDiscoverGigAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView img_gig;
    private FrameLayout frameLayout;
    private String mLokasi;

    Context mContext;

    public DiscoverGigFragment() {
        // Required empty public constructor
    }

    //    List<UserModel> daftarUser = new ArrayList<UserModel>();
//    List<MusicianModel> mDaftarMusician = new ArrayList<MusicianModel>();
    List<Gig> mGig = new ArrayList<Gig>();
    TextView twName;


    private LocationManager mLocationManager;
    private Criteria mCriteria;
    private String mProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_discover_gig_main, container, false);

        mContext = getContext();
        twName = (TextView) view.findViewById(R.id.musician_name);
        /**
         * Location
         */
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mCriteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(mCriteria, false);

        checkPermission();
        if(mProvider!=null) {
            mLocationManager.requestLocationUpdates(mProvider, 4000, 1, this);
        }
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        Call<GigResponse> call = buildUrl.serviceGighub.loadGig();

        buildUrl.serviceGighub.loadGig().enqueue(new Callback<GigResponse>() {
            @Override
            public void onResponse(Call<GigResponse> call, Response<GigResponse> response) {
                if (response.code() == 200) {
                    mGig = response.body().getGigs();
                    mAdapter = new ListDiscoverGigAdapter(mGig);
                    mRecyclerView.setAdapter(mAdapter);
                    String tmp = "";
                    for (int i = 0; i < mGig.size(); i++) {
                        tmp += String.format("" + mGig.get(i).getNama_gig());
//                        mLokasi = mGig.get(i).getLokasi();
                    }
//                    if(strLat.length()>0 && strLng.length()>0){
//                        mAdapter.setMyLatLng(strLat,strLng);
//                    }


                }

            }

            @Override
            public void onFailure(Call<GigResponse> call, Throwable t) {
                Toast.makeText(mContext, "Connection Fail. Check Your Connection", Toast.LENGTH_LONG).show();
//                Log.e("-:failure",t.getMessage());
            }
        });

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.43.152:81/gighub2/public/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

//        ServiceGighub serviceGighub = retrofit.create(ServiceGighub.class);
//        Call<UserResponse> call = serviceGighub.loadUser();
//        RetrofitService.getInstance().getApi().loadMusicians().enqueue(new Callback<MusicianResponse>() {
//            @Override
//            public void onResponse(Call<MusicianResponse> call, Response<MusicianResponse> response) {
//                if(response.body().getError()==0){
//                    mDaftarMusician = response.body().getMusicians();
//                    mAdapter = new ListDiscoverGigAdapter(mDaftarMusician);
//                    mRecyclerView.setAdapter(mAdapter);
//                    String tmp = "";
//                    for(int i =0 ;i<mDaftarMusician.size();i++)
//                    {
//                        tmp += String.format(""+ mDaftarMusician.get(i).getName().toString());
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MusicianResponse> call, Throwable t) {
//                Toast.makeText(mContext,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
////                Log.e("-:failure",t.getMessage());
//            }
//        });


        img_gig = (ImageView) view.findViewById(R.id.img_img_gig_discovergig);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_musician_list);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


//        img_gig.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), GigActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });

        return view;
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return ;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((ListDiscoverGigAdapter) mAdapter).setOnItemClickListener(new ListDiscoverGigAdapter().MyClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                Log.i(LOG_TAG, " Clicked on Item " + position);
//            }
//        });
    }
    private String strLat = "";
    private String strLng = "";
    @Override
    public void onLocationChanged(Location location) { // A point 1
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        Date time = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String waktu = df.format(time).toString();
        Log.d("~e:Location-Change", "Latitude : " + String.valueOf(lat) + " Time : " + waktu);
        Log.d("~e:Location-Change", "Longitude : " + String.valueOf(lng) + " Time : " + waktu);
        if(mAdapter!=null) {
            mAdapter.setMyLatLng(String.valueOf(lat), String.valueOf(lng));
        }else{
            strLat = String.valueOf(lat);
            strLng = String.valueOf(lng);
        }
        Locale bahasaIndonesia = new Locale("in");
        Geocoder geocoder = new Geocoder(getContext(), bahasaIndonesia);

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            List<String> mAlamat = new ArrayList<>();
            for (Address x : addresses
                    ) {
                Log.d("~e:Location-Change", "Name ~" + x.getAddressLine(0) + "");

                Log.d("~e:Location-Change", "Name ~" + x.getAddressLine(1) + "");

                Log.d("~e:Location-Change", "Name ~" + x.getAddressLine(2) + "");

                Log.d("~e:Location-Change", "Name ~" + x.getAddressLine(3) + "");

                Log.d("~e:Location-Change", "Name ~" + x.getAddressLine(4) + "");

                Log.d("~e:Location-Change", "Name ~" + x.getAddressLine(5) + "");

                Log.d("~e:Location-Change", "Name ~1" + x.getSubLocality() + "");
                Log.d("~e:Location-Change", "Name ~2" + x.getFeatureName() + "");
                Log.d("~e:Location-Change", "Name ~3" + x.getLocality() + "");
                Log.d("~e:Location-Change", "Name ~4" + x.getSubAdminArea());
                for (int i = 0; i < x.getMaxAddressLineIndex() - 1; i++) {
                    mAlamat.add(x.getAddressLine(i));
                }

//                mTxtPlace.setHint("Ada " + (x.getMaxAddressLineIndex() - 1) + " Pilihan nama tempat");
                Log.d("~e:Location-Change", "Name ~" + x.getMaxAddressLineIndex() + "");

            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationManager != null) {

            checkPermission();
            mLocationManager.removeUpdates(this);

        }
        Log.e("~e:Recycle", "Pause");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

//    private ArrayList<DataObject> getDataSet() {
//        ArrayList results = new ArrayList<DataObject>();
//        for (int index = 0; index < 20; index++) {
//            DataObject obj = new DataObject("Some Primary Text " + index,
//                    "Secondary " + index);
//            results.add(index, obj);
//        }
//        return results;
//    }

}
