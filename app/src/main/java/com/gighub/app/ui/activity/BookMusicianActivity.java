package com.gighub.app.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Response;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;

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

public class BookMusicianActivity extends AppCompatActivity {

    private Context mContext;
    private Toolbar toolbar;
    private Button mButtonTanggalMulai, mButtonWaktuMulai,mButtonTanggalSelesai,mButtonWaktuSelesai, mButtonSendRequest;
    private TextView mTextViewName, mTextViewGenre, mTextViewHarga;
    private EditText mEditTextNamaGig,mEditTextLokasi, mEditTextDetails;
    private String mName, mGenre,mHarga,mMulai,mSelesai,mTipe;
    private int pos=0, mYear,mMonth,mDay,mHour, mMinute, mUserId,mObjectId;
    private List<SearchResultModel> mSearchResultModel;
    private SessionManager mSession;

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

        mButtonTanggalMulai = (Button)findViewById(R.id.btn_date_waktu_mulai_bookmusicianactivity);
        mButtonWaktuMulai = (Button)findViewById(R.id.btn_time_waktu_mulai_bookmusicianactivity);
        mButtonTanggalSelesai = (Button)findViewById(R.id.btn_date_waktu_selesai_bookmusicianactivity);
        mButtonWaktuSelesai = (Button)findViewById(R.id.btn_time_waktu_selesai_bookmusicianactivity);
        mButtonSendRequest = (Button)findViewById(R.id.btn_send_request_bookmusicianAcivity);

        mEditTextNamaGig = (EditText)findViewById(R.id.et_nama_acara_bookmusicianactivity);
        mEditTextLokasi = (EditText)findViewById(R.id.et_location_bookmusicianactivity);
        mEditTextDetails = (EditText)findViewById(R.id.et_location_details_bookmusicianactivity);

        toolbar.setTitle("Book Request");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        mButtonTanggalMulai.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });



        mButtonWaktuMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR);
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
                        mButtonTanggalSelesai.setText(year+"-"+monthOfYear+"-"+dayOfMonth);

                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });



        mButtonWaktuSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR);
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

        Map<String, String> bookData = new HashMap<>();
        bookData.put("user_id",Integer.toString(mUserId));
        bookData.put("tipe",mTipe);
        bookData.put("object_id",Integer.toString(mObjectId));
        bookData.put("nama_gig",mEditTextNamaGig.getText().toString());
        bookData.put("lokasi", mEditTextLokasi.getText().toString());
        bookData.put("detail_lokasi",mEditTextDetails.getText().toString());
        bookData.put("tanggal_mulai",mMulai);
        bookData.put("tanggal_selesai",mSelesai);

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
                    Log.d("response ", response.code() + " " + response.message() +" "+response.code());
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

}
