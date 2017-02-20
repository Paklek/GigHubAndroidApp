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
import android.provider.MediaStore;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudinary.utils.ObjectUtils;
import com.gighub.app.R;
import com.gighub.app.model.CloudinaryResponse;
import com.gighub.app.model.KonfirmasiPembayaran;
import com.gighub.app.model.KonfirmasiPembayaranResponse;
import com.gighub.app.model.Utility;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.CloudinaryUrl;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.StaticFunction;
import com.gighub.app.util.StaticInt;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiPembayaranActivity extends AppCompatActivity {

    private String[] mBankAdmin = {"BRI","BCA","Mandiri"};
    private String[] mAccountNameAdmin = {"PT. Gighub Indonesia","PT. Gighub Indonesia","PT. Gighub Indonesia",};
    private String[] mBankName = {"BII","BTN","BCA","BRI","Mandiri","BNI"};
    private String[] mAccountNumber = {"230109230827", "09087871824","000092132323"};
    private Spinner mSpinnerBankAdmin, mSpinnerBankName;
    private EditText mEditTextNoRekAdmin,mEditTextNameRekAdmin, mEditTextAccountName, mEditTextAccountNumber;
    private Button mButtonOk,mButtonUploadPaymentBill;
    private Activity activity;
    private Context mContext;
    private int mPos, mSewaId;
    private SessionManager mSession;
    private ImageView mImageViewPaymentBill;
    private String mPilihanUser;
    private static final int REQ_CODE_RESULT_PHOTO = 10002;
    private boolean mFromCamera;
    private File destination;
    private CloudinaryResponse cloudinaryResponse;

    Uri uriGalery = null;
    Map<String,String> responseCloudinary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pembayaran);

        activity = new KonfirmasiPembayaranActivity();

        mSession = new SessionManager(getApplicationContext());
        Intent intent = getIntent();
        mSewaId = intent.getIntExtra("sewa_id",0);
        mContext = getApplicationContext();

        mContext = getApplicationContext();
        mSpinnerBankAdmin = (Spinner)findViewById(R.id.spinner_bank_tujuan_konfirmasipembayaran);
        mSpinnerBankName = (Spinner)findViewById(R.id.spinner_bank_name_konfirmasipembayaran);
        mEditTextNoRekAdmin = (EditText)findViewById(R.id.et_no_rek_admin_konfirmasipembayaran);
        mEditTextNameRekAdmin = (EditText)findViewById(R.id.et_nama_rek_admin_konfirmasipembayaran);
        mEditTextAccountName = (EditText)findViewById(R.id.et_account_name_konfirmasipembayaran);
        mEditTextAccountNumber = (EditText)findViewById(R.id.et_no_rek_konfirmasipembayaran);
        mButtonOk = (Button)findViewById(R.id.btn_ok_konfirmasipembayaran);
        mButtonUploadPaymentBill = (Button)findViewById(R.id.btn_upload_payment_bill_photo_konfirmasipembayaran);
        mImageViewPaymentBill = (ImageView)findViewById(R.id.img_photo_payment_bill_konfirmasipembayaran);

        mEditTextNameRekAdmin.setFocusable(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mBankAdmin);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinnerBankAdmin.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mPos = 0;
        mSpinnerBankAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerBankAdmin.setOnItemSelectedListener(this);
                mEditTextNoRekAdmin.setText(mAccountNumber[position]);
                mEditTextNameRekAdmin.setText(mAccountNameAdmin[position]);
                mPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,mBankName);
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinnerBankName.setAdapter(adapter2);

        mEditTextNoRekAdmin.setFocusable(false);

        mSpinnerBankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerBankName.setOnItemSelectedListener(this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mButtonUploadPaymentBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDataKonfirmasiPembayaran();
            }
        });

    }

    private void selectImage(){
        final CharSequence[] items = { "From Camera", "From Gallery",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(KonfirmasiPembayaranActivity.this);
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


    Map<String, String> dataKonfirmasiPembayaran = new HashMap<>();
    private void SendDataKonfirmasiPembayaran(){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();
        cloudinaryResponse = new Gson().fromJson(new Gson().toJson(responseCloudinary),CloudinaryResponse.class);

        if(mSession.checkUserType().equals("org")) {
            dataKonfirmasiPembayaran.put("tipe_user", "org");
        }
        dataKonfirmasiPembayaran.put("nama_bank", mSpinnerBankName.getSelectedItem().toString());
        dataKonfirmasiPembayaran.put("nama_rek", mEditTextAccountName.getText().toString());
        dataKonfirmasiPembayaran.put("no_rek", mEditTextAccountNumber.getText().toString());
        dataKonfirmasiPembayaran.put("sewa_id",Integer.toString(mSewaId));
        if(mSpinnerBankAdmin.getSelectedItem().equals("BRI")){
            dataKonfirmasiPembayaran.put("bank_admin_id","1");
        }
        if(mSpinnerBankAdmin.getSelectedItem().equals("BCA")){
            dataKonfirmasiPembayaran.put("bank_admin_id","2");
        }
        if(mSpinnerBankAdmin.getSelectedItem().equals("Mandiri")){
            dataKonfirmasiPembayaran.put("bank_admin_id","3");
        }
        if(cloudinaryResponse!=null) {
            if (!cloudinaryResponse.getPublic_id().equals("") || cloudinaryResponse.getPublic_id() != null) {
                dataKonfirmasiPembayaran.put("photo", cloudinaryResponse.getPublic_id());
            }
        }
        else {
            dataKonfirmasiPembayaran.put("photo_gig", "");
        }

        buildUrl.serviceGighub.sendDataKonfirmasiPembayaran(dataKonfirmasiPembayaran).enqueue(new Callback<KonfirmasiPembayaranResponse>() {
            @Override
            public void onResponse(Call<KonfirmasiPembayaranResponse> call, Response<KonfirmasiPembayaranResponse> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(mContext,MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(KonfirmasiPembayaranActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("response " , response.code() +" "+ response.body().getMessage());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
//                    finish();
                }
                else {
                    Log.d("Pesan Log : " , response.code() + response.message());
                    Toast.makeText(KonfirmasiPembayaranActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<KonfirmasiPembayaranResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
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
            progressDialog = new ProgressDialog(KonfirmasiPembayaranActivity.this);
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
            String url = StaticFunction.get(KonfirmasiPembayaranActivity.this).getRealBaseURL(uri);
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
    private void onSelectFromGalleryResult(Intent data) {
        uriGalery = data.getData();
        responseCloudinary = new HashMap<>();
//        ProgressDialog progressDialog = new ProgressDialog(mContext);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Uploading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        new KonfirmasiPembayaranActivity.uploadImageAsync(uriGalery).execute();
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(KonfirmasiPembayaranActivity.this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mImageViewPaymentBill.setImageBitmap(bm);
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
        Uri tempUri = getImageUri(KonfirmasiPembayaranActivity.this, thumbnail);

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

        new KonfirmasiPembayaranActivity.uploadImageAsync(tempUri).execute();
//        Bitmap bm=null;
//        if (data != null) {
//            try {
//                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),Uri.parse(destination.toString()));
////                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        mImageViewPaymentBill.setImageBitmap(thumbnail);

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
}
