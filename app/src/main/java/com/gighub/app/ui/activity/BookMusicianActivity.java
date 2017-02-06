package com.gighub.app.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cloudinary.utils.ObjectUtils;
import com.gighub.app.R;
import com.gighub.app.model.CloudinaryResponse;
import com.gighub.app.model.GigResponse;
import com.gighub.app.model.Response;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.model.Utility;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.StaticFunction;
import com.gighub.app.util.StaticInt;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class BookMusicianActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Context mContext;
    private Toolbar toolbar;
    private Button mButtonTanggalMulai, mButtonWaktuMulai,mButtonTanggalSelesai,mButtonWaktuSelesai, mButtonSendRequest,mButtonUploadGigPhoto;
    private TextView mTextViewName, mTextViewGenre, mTextViewHarga;
    private EditText mEditTextNamaGig,mEditTextLokasi, mEditTextDetails;
    private String mName, mGenre,mHarga,mMulai,mSelesai,mTipe, mLat, mLng, mPilihanUser;
    private int pos=0, mYear,mMonth,mDay,mHour, mMinute, mUserId,mObjectId;
    private List<SearchResultModel> mSearchResultModel;
    private SessionManager mSession;
    private CloudinaryResponse cloudinaryResponse;
    private ImageView mImageViewGigPhoto;
    private File destination;


    private static final String TYPE_GEOCODE = "/geocode";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api";
    private static final String LOG_TAG = "GooglePlaces";
    private static final String OUT_JSON = "/json";

    public static final String API_KEY = "AIzaSyCnr-Sxlgu9soj-V9u4xaoP1kDEy3ULW3A";

    private static final int REQ_CODE_RESULT_PHOTO = 10002;
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_musician);

        mSession = new SessionManager(getApplicationContext());
        mContext = getApplicationContext();
        mSearchResultModel = new ArrayList<SearchResultModel>();

        Intent intent = getIntent();
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        mTextViewName = (TextView)findViewById(R.id.tv_musician_name_bookingmusicianactivity);
        mTextViewGenre = (TextView)findViewById(R.id.tv_genre_bookmusicianactivity);
        mTextViewHarga = (TextView)findViewById(R.id.tv_harga_sewa_bookmusicianactivity);
        mImageViewGigPhoto = (ImageView)findViewById(R.id.img_photo_gig_bookmusicianactivity);

        mButtonTanggalMulai = (Button)findViewById(R.id.btn_date_waktu_mulai_bookmusicianactivity);
        mButtonWaktuMulai = (Button)findViewById(R.id.btn_time_waktu_mulai_bookmusicianactivity);
        mButtonTanggalSelesai = (Button)findViewById(R.id.btn_date_waktu_selesai_bookmusicianactivity);
        mButtonWaktuSelesai = (Button)findViewById(R.id.btn_time_waktu_selesai_bookmusicianactivity);
        mButtonSendRequest = (Button)findViewById(R.id.btn_send_request_bookmusicianAcivity);
        mButtonUploadGigPhoto = (Button)findViewById(R.id.btn_upload_gig_photo_bookmusicianactivity);

        mEditTextNamaGig = (EditText)findViewById(R.id.et_nama_acara_bookmusicianactivity);
        mEditTextLokasi = (EditText)findViewById(R.id.et_location_bookmusicianactivity);
        mEditTextDetails = (EditText)findViewById(R.id.et_location_details_bookmusicianactivity);

        toolbar.setTitle("Book Request");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mName = intent.getStringExtra("name");
        mGenre = intent.getStringExtra("genre");
        mHarga = intent.getStringExtra("harga_sewa");
        mObjectId = intent.getIntExtra("id",0);
        mTipe = intent.getStringExtra("tipe");

        mUserId = mSession.getUserDetails().getId();

        mTextViewName.setText(mName);
        mTextViewGenre.setText(mGenre);
        mTextViewHarga.setText(mHarga);

        Log.d("name",mName);

        mButtonUploadGigPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        mEditTextLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(BookMusicianActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });


        mButtonTanggalMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getLayoutInflater().getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mButtonTanggalMulai.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });



        mButtonWaktuMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);




                TimePickerDialog timePickerDialog = new TimePickerDialog(getLayoutInflater().getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String waktu = String.format("%02d:%02d",hourOfDay,minute);
                        mButtonWaktuMulai.setText(waktu+":00");
                    }
                },mHour,mMinute, android.text.format.DateFormat.is24HourFormat(getApplicationContext()));

                timePickerDialog.show();
            }
        });




        mButtonTanggalSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getLayoutInflater().getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mButtonTanggalSelesai.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);

                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });



        mButtonWaktuSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getLayoutInflater().getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String waktu = String.format("%02d:%02d",hourOfDay,minute);
                        mButtonWaktuSelesai.setText(waktu+":00");
                    }
                },mHour,mMinute, android.text.format.DateFormat.is24HourFormat(getApplicationContext()));


                timePickerDialog.show();
            }
        });


        mButtonSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBook();
            }
        });

    }

    private void sendBook(){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        mMulai = mButtonTanggalMulai.getText().toString()+" "+mButtonWaktuMulai.getText().toString();
        mSelesai = mButtonTanggalSelesai.getText().toString()+" "+mButtonWaktuSelesai.getText().toString();

        cloudinaryResponse = new Gson().fromJson(new Gson().toJson(responseCloudinary),CloudinaryResponse.class);

        Map<String, String> bookData = new HashMap<>();
        bookData.put("user_id",Integer.toString(mUserId));
        bookData.put("tipe",mTipe);
        bookData.put("object_id",Integer.toString(mObjectId));
        bookData.put("nama_gig",mEditTextNamaGig.getText().toString());
        bookData.put("lokasi", mEditTextLokasi.getText().toString());
        if(cloudinaryResponse!=null) {
            if (!cloudinaryResponse.getPublic_id().equals("") || cloudinaryResponse.getPublic_id() != null) {
                bookData.put("photo_gig", cloudinaryResponse.getPublic_id());
            }
        }
        else {
            bookData.put("photo_gig", "");
        }
        bookData.put("detail_lokasi",mEditTextDetails.getText().toString());
        bookData.put("tanggal_mulai",mMulai);
        bookData.put("tanggal_selesai",mSelesai);
        bookData.put("lat",mLat);
        bookData.put("lng",mLng);

        buildUrl.serviceGighub.sendBookData(bookData).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if(response.code()==200) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    Toast.makeText(BookMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("response ", response.code() + " " + response.body().getMessage());
                    Log.d("response ", response.code() + " " + response.message() +" "+response.code());

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(intent);
                }
                else {
                    Log.d("response ", response.code() + " " + response.body().getMessage() +" "+response.code());
                    Log.d("Pesan Log : " , response.code()+" " + response.message());
                    Toast.makeText(BookMusicianActivity.this, "Failed, Check Your Connection", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("fail",t.getCause().getMessage());
            }
        });

    }

    private ArrayList autocomplete(String input) {
        ArrayList mLocationList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_GEOCODE + OUT_JSON);
            sb.append("?address=" + URLEncoder.encode(input, "utf8"));
            sb.append("&components=country:id");
            sb.append("&sensor=true");
            sb.append("&?key=" + API_KEY);
//                sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL" + e);
            return mLocationList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API" + e);
            return mLocationList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
//                JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");
            JSONArray locationJsonArray = jsonObj.getJSONArray("location");
            // Extract the Place descriptions from the results
            mLocationList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                JSONObject jsonResult = locationJsonArray.getJSONObject(i);
                System.out.println(predsJsonArray.getJSONObject(i).getString("formatted_address"));
                System.out.println("============================================================");
                mLocationList.add(predsJsonArray.getJSONObject(i).getString("formatted_address"));
                mLat = jsonResult.getString("lat");
//                    mLat = locationJsonArray.getJSONObject(i).getString("location");
                mLng = predsJsonArray.getJSONObject(i).getJSONObject("location").getString("lng");
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }


        return mLocationList;
    }




    private LatLng latlng = new LatLng(0,0);
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE){
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("Lokasi", "Place: " + place.getName());
                mEditTextLokasi.setText(place.getAddress());
                latlng = place.getLatLng();
                mLat = Double.toString(place.getLatLng().latitude);
                mLng = Double.toString(place.getLatLng().longitude);

                Log.d("LatLng ",""+latlng);
            }
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == StaticInt.SELECT_FILE) {
                    onSelectFromGalleryResult(data);
                }
                else if (requestCode == StaticInt.REQUEST_CAMERA){

                    onCaptureImageResult(data);
                }else if(requestCode == REQ_CODE_RESULT_PHOTO){

                    onSelectFromGalleryResult(data);

                }
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImageViewGigPhoto.setImageBitmap(thumbnail);

    }

    private void onSelectFromGalleryResult(Intent data) {
        uriGalery = data.getData();
        responseCloudinary = new HashMap<>();
//        ProgressDialog progressDialog = new ProgressDialog(mContext);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Uploading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        new BookMusicianActivity.uploadImageAsync(uriGalery).execute();
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mImageViewGigPhoto.setImageBitmap(bm);
//        progressDialog.dismiss();
    }


    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        String str = (String) parent.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

    }

    Uri uriGalery = null;
    Map<String,String> responseCloudinary;

//    private void uploadGigPhoto(){
//        cloudinaryResponse = new Gson().fromJson(new Gson().toJson(responseCloudinary),CloudinaryResponse.class);
//
//        BuildUrl buildUrl = new BuildUrl();
//        buildUrl.buildBaseUrl();
//    }

    public class uploadImageAsync extends AsyncTask {
        Uri uri;
        //        ProgressDialog progressDialog;
        ProgressDialog progressDialog;
        //        progressDialog.s(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Uploading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        public uploadImageAsync(Uri uri) {

            // nanti show loading disini (ProgressDialog)
//            StaticFunction.get(mContext).buildProgressDialog(mContext);
            progressDialog = new ProgressDialog(BookMusicianActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Uploading...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            Log.d("infoin","prepeare");


            this.uri = uri;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String url = StaticFunction.get(getApplicationContext()).getRealBaseURL(uri);
            try{
//                responseCloudinary = new Cloudinary().uploader().upload(url, ObjectUtils.emptyMap());
                CloudinaryUrl cloudinaryUrl =new CloudinaryUrl();
                cloudinaryUrl.buildCloudinaryUrl();
                responseCloudinary = cloudinaryUrl.cloudinary.uploader().upload(url, ObjectUtils.emptyMap());
            }catch(IOException e){

            }
            if(!responseCloudinary.isEmpty()){
                // nanti dismiss loading disini (ProgressDialog)
//                progressDialog.dismiss();
                Log.d("infoin","Sukses");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            updateTableUser();
//            Toast.makeText(ProfileActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
//            doUploadGigPhoto();
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            ProgressDialog progressDialog = new ProgressDialog(mContext);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage("Uploading...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
        }
    }
    private void selectImage(){
        final CharSequence[] items = { "From Camera", "From Gallery",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(BookMusicianActivity.this);
        builder.setTitle("Choose Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = Utility.checkPermission(mContext);
                if (items[which].equals("From Camera")){
                    mPilihanUser = "From Camera";
                    if(result){
                        cameraIntent();
                    }
                }
                else if(items[which].equals("From Gallery")){
                    mPilihanUser = "From Gallery";
                    if (result){
                        galleryIntent();
                    }
                }
                else if (items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_RESULT_PHOTO);
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select File"),StaticInt.SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mPilihanUser.equals("From Camera"))
                        cameraIntent();
                    else if(mPilihanUser.equals("From Gallery"))
                        galleryIntent();
                } else {
//code for deny
                }
                break;
        }
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, StaticInt.REQUEST_CAMERA);
    }

//    Map<String, String> gigPhoto = new HashMap<>();
//
//    private void doUploadGigPhoto(){
//        cloudinaryResponse = new Gson().fromJson(new Gson().toJson(responseCloudinary),CloudinaryResponse.class);
//
//        BuildUrl buildUrl = new BuildUrl();
//        buildUrl.buildBaseUrl();
//
//        gigPhoto.put("photo_gig",cloudinaryResponse.getPublic_id());
//
//        buildUrl.serviceGighub.sendGigPhoto(gigPhoto).enqueue(new Callback<Response>() {
//            @Override
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                if(response.code()==200){
//                    Toast.makeText(BookMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Response> call, Throwable t) {
//                Toast.makeText(BookMusicianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
