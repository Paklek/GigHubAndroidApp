package com.gighub.app.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudinary.utils.ObjectUtils;
import com.gighub.app.R;
import com.gighub.app.model.CloudinaryResponse;
import com.gighub.app.model.Genre;
import com.gighub.app.model.GrupBandResponse;
import com.gighub.app.model.Position;
import com.gighub.app.model.PositionResponse;
import com.gighub.app.model.Utility;
import com.gighub.app.ui.adapter.ListGenreCreateBandAdapter;
import com.gighub.app.ui.adapter.ListPositionAdapter;
import com.gighub.app.ui.fragment.DialogGenreFragment;
import com.gighub.app.ui.fragment.DialogPositionCreatBandFragment;
import com.gighub.app.ui.fragment.DiscoverMusicianFragment;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.StaticFunction;
import com.gighub.app.util.StaticInt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBandActivity extends AppCompatActivity {

    private EditText mEditTextNamaBand, mEditTextDeskripsiBand, mEditTextHargaBand, mEditTextKota,mEditTextSelectGenres, mEditTextSelectPosition;
    private Button mButtonCreateBand,mButtonUploadGigPhoto;
    private CheckBox mCheckBoxGenrePop, mCheckBoxGenreRock;
    private SessionManager mSession;
    private String mMusicianId,mPosition, mGenreDipilih ="", mPilihanUser;
    private Context mContext;
    private Spinner mSpinnerPosition;
    private CheckBox[] cbxs = new CheckBox[5];
    private LinearLayout mLinearLayoutCheckBoxGenre;
    private String[] genres= new String[5];
    private GridView mGridView;
    private List<Genre> mGenres;

    private CloudinaryResponse cloudinaryResponse;
    private ImageView mImageViewBandPhoto;
    private File destination;
    private boolean mFromCamera;




    private int mPositionId,mCount=0;

    private String[] mListPosition={"Position","Vocalist","Guitarist","Bassist","Drummer","Keyboardist"};

    public static final String CREATEBAND = "createband";
    public static final int REQQODE = 1000;
    private static final int REQ_CODE_RESULT_PHOTO = 10002;

    DialogFragment dialogFragment;
    private ListGenreCreateBandAdapter listGenreCreateBandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_band);
        mContext = getApplicationContext();

        final Activity activity = this;

        mGenres = new ArrayList<Genre>();

        mSession = new SessionManager(getApplicationContext());

        mGridView = (GridView)findViewById(R.id.lv_genres_createband);
        mLinearLayoutCheckBoxGenre = (LinearLayout)findViewById(R.id.ll_list_genre);
        mEditTextSelectPosition = (EditText)findViewById(R.id.et_selectposition_createband);
        mImageViewBandPhoto = (ImageView)findViewById(R.id.img_photo_band_createband);
        mButtonUploadGigPhoto = (Button)findViewById(R.id.btn_upload_band_photo_createband);

        final Intent intent = getIntent();
        final Type type = new TypeToken<List<Genre>>(){}.getType();
        mGenres = new Gson().fromJson(intent.getStringExtra("genres"),type);


        mButtonUploadGigPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        listGenreCreateBandAdapter = new ListGenreCreateBandAdapter(mContext,mGenres);

        mGridView.setAdapter(listGenreCreateBandAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CreateBandActivity.this,mGenres.get(position).getGenre_name(),Toast.LENGTH_SHORT).show();
                Log.d("onItemClick",""+mGenres.get(position).getGenre_name());
//                mGridView.getChildAt(position);
//                Log.d("isSelected",""+parent.getChildAt(position).isSelected());
//                Log.d("isPressed",""+parent.getChildAt(position).isPressed());
//                Log.d("isSelected",""+parent.getChildAt(position).is);
                Log.d("isSelected",""+mGridView.getChildAt(position).isSelected());
                Log.d("isPressed",""+mGridView.getChildAt(position).isPressed());
                Log.d("isSelected",""+mGridView.isItemChecked(position));

//                Log.d("isSelected",""+selected[position]);
                if (!mGenres.get(position).getSelected()){
                    mGridView.getChildAt(position).setBackground(mGridView.getResources().getDrawable(R.drawable.button_border_black));
                    mGenres.get(position).setSelected(true);
                    mGenreDipilih += " "+mGenres.get(position).getId();
                    mCount +=1;
                }
                else
                {
                    mGridView.getChildAt(position).setBackground(mGridView.getResources().getDrawable(R.drawable.spinner_drawable));
                    mGenres.get(position).setSelected(false);
                    mGenreDipilih = mGenreDipilih.replace(" "+mGenres.get(position).getId(),"");
                    mCount -= 1;

                }

            }
        });

//        for(int i=0;i<cbxs.length;i++){
//            cbxs[i] = new CheckBox(mContext);
//            if(i==0){
//                cbxs[i].setText("Pop");
//            }
//            if(i==1){
//                cbxs[i].setText("Rock");
//            }
//            if(i==2){
//                cbxs[i].setText("Jazz");
//            }
//            if(i==3){
//                cbxs[i].setText("Dangdut");
//            }
//            if(i==4){
//                cbxs[i].setText("Reggeae");
//            }
//            cbxs[i].setPadding(10,10,10,10);
//            cbxs[i].setButtonDrawable(this.getResources().getDrawable(R.drawable.custom_checkbox));
//            cbxs[i].setTextColor(this.getResources().getColor(R.color.colorTextDark));
//            mLinearLayoutCheckBoxGenre.addView(cbxs[i]);
//        }

        mEditTextNamaBand = (EditText)findViewById(R.id.et_nama_band_createband);
        mEditTextDeskripsiBand = (EditText)findViewById(R.id.et_band_descriptions_createband);
        mEditTextHargaBand = (EditText)findViewById(R.id.et_harga_sewa_createband);
        mEditTextKota = (EditText)findViewById(R.id.spinner_kota_band_createband);

//        mSpinnerPosition = (Spinner)findViewById(R.id.spinner_position_createbandactivity);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.spinner_item_position,mListPosition);
//        adapter.setDropDownViewResource(R.layout.spinner_item_position);
//        mSpinnerPosition.setAdapter(adapter);
//
        mEditTextSelectPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();

                buildUrl.serviceGighub.callPosition().enqueue(new Callback<PositionResponse>() {
                    @Override
                    public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
//                        Intent i = new Intent(mContext,DialogPositionCreatBandFragment.class);
                        dialogFragment = new DialogPositionCreatBandFragment();
                        dialogFragment.show(getSupportFragmentManager(),"position");

//                        Bundle args = new Bundle();
                    }

                    @Override
                    public void onFailure(Call<PositionResponse> call, Throwable t) {

                    }
                });

            }
        });

//        mCheckBoxGenrePop = (CheckBox)findViewById(R.id.cbx_genre_pop_createbandactivity);
//        mCheckBoxGenreRock = (CheckBox)findViewById(R.id.cbx_genre_rock_createbandactivity);

//        mEditTextSelectGenres = (EditText)findViewById(R.id.et_select_genre_createbandactivity);
//        mEditTextSelectGenres.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment dialogFragment = new DialogGenreFragment();
//                dialogFragment.show(getSupportFragmentManager(),Integer.toString(REQQODE));
//            }
//        });

//
//        cbxs[0].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(0);
//            }
//        });
//        cbxs[1].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(1);
//            }
//        });
//        cbxs[2].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(2);
//            }
//        });
//        cbxs[3].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(3);
//            }
//        });
//        cbxs[4].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(4);
//            }
//        });


//        mSpinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mPosition = mSpinnerPosition.getAdapter().getItem(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        mMusicianId = Integer.toString(mSession.getMusicianDetails().getId());

        mButtonCreateBand = (Button)findViewById(R.id.btn_create_band_createband);

        mButtonCreateBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBand();
            }
        });

    }

    Map<String,String> dataBand = new HashMap<>();
//    Map<String,String[]> dataArray = new HashMap<>();
    Uri uriGalery = null;
    Map<String,String> responseCloudinary;


    private void insertBand(){

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        cloudinaryResponse = new Gson().fromJson(new Gson().toJson(responseCloudinary),CloudinaryResponse.class);

//        if (mPosition.equals("Vocalist")){
//            mPositionId = 1;
//        }
//        else  if(mPosition.equals("Guitarist")){
//            mPositionId = 2;
//        }
//        else if(mPosition.equals("Bassist")){
//            mPositionId = 3;
//        }
//        else if(mPosition.equals("Drummer")){
//            mPositionId = 4;
//        }
//        else if(mPosition.equals("Keyboardist")){
//            mPositionId = 5;
//        }
//        else mPosition="Position";




        mGenreDipilih = mGenreDipilih.substring(1,mGenreDipilih.length());
        Log.d("genreyangdikirim",mGenreDipilih);

        dataBand.put("genre_count",Integer.toString(mCount));
        dataBand.put("genre_id",mGenreDipilih);
        dataBand.put("admin_id",mMusicianId);
        dataBand.put("nama_grupband",mEditTextNamaBand.getText().toString());
        if(cloudinaryResponse!=null) {
            if (!cloudinaryResponse.getPublic_id().equals("") || cloudinaryResponse.getPublic_id() != null) {
                dataBand.put("photo", cloudinaryResponse.getPublic_id());
            }
        }
        else {
            dataBand.put("photo", "");
        }
        dataBand.put("harga",mEditTextHargaBand.getText().toString());
        dataBand.put("deskripsi",mEditTextDeskripsiBand.getText().toString());
        dataBand.put("kota", mEditTextKota.getText().toString());
        dataBand.put("posisi", Integer.toString(mPositionId));
        Log.d("posisi",""+mPositionId);
        Log.d("genre_count",""+mCount);

        buildUrl.serviceGighub.sendInsertDataBand(dataBand).enqueue(new Callback<GrupBandResponse>() {
            @Override
            public void onResponse(Call<GrupBandResponse> call, Response<GrupBandResponse> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(mContext,MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(CreateBandActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Log.d(CREATEBAND,"response " +response.code() +" "+ response.body().getMessage());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(intent);
                }
                else {
                    Log.d(CREATEBAND, "Pesan Log : " + response.code() + response.message());
                    Toast.makeText(CreateBandActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
//                Log.d(CREATEBAND,"code" +response.code()+" "+response.message());
            }

            @Override
            public void onFailure(Call<GrupBandResponse> call, Throwable t) {

            }
        });

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==CreateBandActivity.REQQODE){
//            List<Genre> genreList = new ArrayList<>();
//            Type typeGenreList = new TypeToken<List<Genre>>(){}.getType();
//            genreList = new Gson().fromJson(data.getStringExtra("kirim"),typeGenreList);
//            String genres = "";
//            for(Genre g:genreList) {
//                if(g.getSelected()){
//                    genres += g.getGenre_name()+" ";
//                }
//            }
//            mEditTextSelectGenres.setText(genres);
////            for(int i=0;i)
//        }
//    }

//    private void insertGenre (int checkBoxIndex){
//        if(cbxs[checkBoxIndex].isChecked()) {
//            Log.d("genre", cbxs[checkBoxIndex].getText().toString());
//            genres[checkBoxIndex] = " "+(checkBoxIndex+1);
//            mGenreDipilih += genres[checkBoxIndex];
//            mCount+=1;
//        }
//        else{
//            mGenreDipilih = mGenreDipilih.replace(" "+(checkBoxIndex+1),"");
//            mCount-=1;
//        }
//        Log.d("genreyangdipilih",mGenreDipilih);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== ListPositionAdapter.REQ_POSITION_CODE) {
            List<Position> positionList = new ArrayList<Position>();
            Type typePositionList = new TypeToken<List<Position>>() {
            }.getType();
            positionList = new Gson().fromJson(data.getStringExtra("posisi"), typePositionList);
            String positions = "";
            int idPosition=0;
//            for(Position position:positionList) {
//                if(position.isSelected()){
////                    if(!genres.equals("")){
////                        genres += ","+g.getGenre_name();
////                    }
////                    else {
////                        genres += g.getGenre_name();
////                    }
////                    idPosition = position.getId();
//                    positions +=""+position.getPosition_name();
//                    idPosition += ""+Integer.toString(position.getId());
//                }
//            }
//            for(int i=0;i<positionList.size();i++){
//                if(positionList.get(i).isSelected()){
//                    positions +=""+positionList.get(i).getPosition_name();
//                    idPosition += positionList.get(i).getId();
//                }
//            }
//
////            mPositionId = idPosition;
//            mEditTextSelectPosition.setText(positions+" Ok");
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

    private void onSelectFromGalleryResult(Intent data) {
        uriGalery = data.getData();
        responseCloudinary = new HashMap<>();
//        ProgressDialog progressDialog = new ProgressDialog(mContext);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Uploading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        new CreateBandActivity.uploadImageAsync(uriGalery).execute();
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(CreateBandActivity.this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mImageViewBandPhoto.setImageBitmap(bm);
//        progressDialog.dismiss();
    }

    private void onCaptureImageResult(Intent data) {
        mFromCamera = true;
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        responseCloudinary = new HashMap<>();
        uriGalery = data.getData();
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(CreateBandActivity.this, thumbnail);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        destination = new File(getRealPathFromURI(tempUri));
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        new CreateBandActivity.uploadImageAsync(tempUri).execute();
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        mImageViewBandPhoto.setImageBitmap(thumbnail);

    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return Uri.parse(path);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onGetData(Position position){
        Log.d("Data Received",position.getPosition_name());
        mEditTextSelectPosition.setText(position.getPosition_name());
        mPositionId = position.getId();
    }



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
            progressDialog = new ProgressDialog(CreateBandActivity.this);
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
            String url = StaticFunction.get(CreateBandActivity.this).getRealBaseURL(uri);
            String url2 = getRealPathFromURI(uri);

            if (!mFromCamera) {
                try {
//                responseCloudinary = new Cloudinary().uploader().upload(url, ObjectUtils.emptyMap());
                    CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
                    cloudinaryUrl.buildCloudinaryUrl();
                    responseCloudinary = cloudinaryUrl.cloudinary.uploader().upload(url, ObjectUtils.emptyMap());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
//                responseCloudinary = new Cloudinary().uploader().upload(url, ObjectUtils.emptyMap());
                    CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
                    cloudinaryUrl.buildCloudinaryUrl();
                    responseCloudinary = cloudinaryUrl.cloudinary.uploader().upload(url2, ObjectUtils.emptyMap());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateBandActivity.this);
        builder.setTitle("Choose Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = Utility.checkPermission(CreateBandActivity.this);
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
}
