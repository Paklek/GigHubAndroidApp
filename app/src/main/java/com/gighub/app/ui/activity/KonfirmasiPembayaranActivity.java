package com.gighub.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.KonfirmasiPembayaran;
import com.gighub.app.model.KonfirmasiPembayaranResponse;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;

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
    private Button mButtonOk;
    private Activity activity;
    private Context mContext;
    private int mPos, mSewaId;
    private SessionManager mSession;

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

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDataKonfirmasiPembayaran();
            }
        });

    }

    Map<String, String> dataKonfirmasiPembayaran = new HashMap<>();
    private void SendDataKonfirmasiPembayaran(){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

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
}
