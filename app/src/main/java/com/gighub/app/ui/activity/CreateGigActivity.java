package com.gighub.app.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.GigResponse;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGigActivity extends AppCompatActivity {

    private TextView mTextViewGigName, mTextViewLocation, mTextViewDetails, mTextViewDescriptions;
    private Button mButtonTanggalMulai, mButtonWaktuMulai, mButtonTanggalSelesai, mButtonWaktuSelesai, mButtonCreateGig;
    private int mOrganizerId, mYear,mMonth,mDay,mHour,mMinute;
    private String mTanggalMulai, mTanggalSelesai;
    private SessionManager mSession;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_gig);

        mContext = getApplicationContext();

        mSession = new SessionManager(getApplicationContext());

        mTextViewGigName = (TextView)findViewById(R.id.tv_nama_acara_creategigactivity);
        mTextViewLocation = (TextView)findViewById(R.id.tv_location_creategigactivity);
        mTextViewDetails = (TextView)findViewById(R.id.tv_location_details_creategigactivity);
        mTextViewDescriptions = (TextView)findViewById(R.id.tv_deskripsi_creategigactivity);

        mButtonTanggalMulai = (Button)findViewById(R.id.btn_date_waktu_mulai_creategigactivity);
        mButtonWaktuMulai = (Button)findViewById(R.id.btn_time_waktu_mulai_creategigactivity);
        mButtonTanggalSelesai = (Button)findViewById(R.id.btn_date_waktu_selesai_creategigactivity);
        mButtonWaktuSelesai = (Button)findViewById(R.id.btn_time_waktu_selesai_creategigactivity);
        mButtonCreateGig = (Button)findViewById(R.id.btn_create_gig_creategigacivity);

        mOrganizerId = mSession.getUserDetails().getId();





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
//                mSecond = c.get(Calendar.SECOND);



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
//                mSecond = c.get(Calendar.SECOND);

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



        mButtonCreateGig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGig();
            }
        });
    }

    Map<String,String> dataGig = new HashMap<>();

    private void insertGig(){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        mTanggalMulai = mButtonTanggalMulai.getText().toString()+" "+ mButtonWaktuMulai.getText().toString();
        mTanggalSelesai = mButtonTanggalSelesai.getText().toString()+" "+mButtonWaktuSelesai.getText().toString();

        dataGig.put("user_id",Integer.toString(mOrganizerId));
        dataGig.put("nama_gig",mTextViewGigName.getText().toString());
        dataGig.put("lokasi", mTextViewLocation.getText().toString());
        dataGig.put("detail_lokasi", mTextViewDetails.getText().toString());
        dataGig.put("deskripsi",mTextViewDescriptions.getText().toString());
        dataGig.put("tanggal_mulai",mTanggalMulai);
        dataGig.put("tanggal_selesai",mTanggalSelesai);


        buildUrl.serviceGighub.sendInsertDataGig(dataGig).enqueue(new Callback<GigResponse>() {
            @Override
            public void onResponse(Call<GigResponse> call, Response<GigResponse> response) {
                Intent intent = new Intent(mContext,MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(CreateGigActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                Log.d(CREATEBAND,"response " +response.code() +" "+ response.body().getMessage());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
            }

            @Override
            public void onFailure(Call<GigResponse> call, Throwable t) {

            }
        });
    }
}
