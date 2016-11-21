package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gighub.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewMapGig extends AppCompatActivity implements OnMapReadyCallback {

    private static double startLatitude;
    private static double startLongitude;
    private static double endLatitude;
    private static double endLongitude;
    private static float[] results;
    GoogleMap mGoogleMap;
    private String mLat,mLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map_gig);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        Intent intent1 = getIntent();
        mLat = intent1.getStringExtra("lat");
        mLng = intent1.getStringExtra("lng");
        LatLng mLatLng;

        if((mLat.equals("") && mLng.equals("")) || (mLat==null && mLng==null)) {
            mLatLng = new LatLng(3.495964, 98.682958);
            mGoogleMap.addMarker(new MarkerOptions().position(mLatLng).title("Lokasi Gig"));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
        }
        else {
            mLatLng = new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLng));
            mGoogleMap.addMarker(new MarkerOptions().position(mLatLng).title("Lokasi Gig"));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
        }
//        mGoogleMap.addCircle(new CircleOptions().center(sydney));
//        mGoogleMap.get

    }

    
}
