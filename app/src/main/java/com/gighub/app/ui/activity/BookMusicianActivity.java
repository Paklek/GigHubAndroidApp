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
import android.widget.TextView;
import android.widget.TimePicker;

import com.gighub.app.R;
import com.gighub.app.model.SearchResultModel;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookMusicianActivity extends AppCompatActivity {

    private Context mContext;
    private Toolbar toolbar;
    private Button mButtonTanggalMulai, mButtonWaktuMulai,mButtonTanggalSelesai,mButtonWaktuSelesai;
    private TextView mTextViewName, mTextViewGenre, mTextViewHarga;
    private String mName, mGenre,mHarga;
    private int pos=0, mYear,mMonth,mDay,mHour, mMinute, mSecond;
    private List<SearchResultModel> mSearchResultModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_musician);

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

        toolbar.setTitle("Book Request");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = intent.getStringExtra("name");
        mGenre = intent.getStringExtra("genre");
        mHarga = intent.getStringExtra("harga_sewa");

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
                mSecond = c.get(Calendar.SECOND);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getLayoutInflater().getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mButtonWaktuMulai.setText(hourOfDay+":"+minute+":00");
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
                mSecond = c.get(Calendar.SECOND);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getLayoutInflater().getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mButtonWaktuSelesai.setText(hourOfDay+":"+minute+":00");
                    }
                },mHour,mMinute, android.text.format.DateFormat.is24HourFormat(getApplicationContext()));

                timePickerDialog.show();
            }
        });

    }
}
