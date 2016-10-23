package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.util.SessionManager;

import org.w3c.dom.Text;

public class BookingDetailsActivity extends AppCompatActivity {

    private Button mButtonConfirmPayment;
    private TextView mTextViewNamaGig, mTextViewNamaMusisi, mTextViewUserName, mTextViewLocation, mTextViewHarga, mTextViewWaktuMulai, mTextViewWaktuSelesai, mTextViewTotal;
    private SessionManager mSession;
    private int mSewaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        mSession = new SessionManager(getApplicationContext());
        Intent intent = getIntent();

        mTextViewNamaMusisi = (TextView)findViewById(R.id.tv_nama_grupband_bookdetails);
        mTextViewUserName = (TextView)findViewById(R.id.tv_nama_user_bookdetails);
        mTextViewLocation = (TextView)findViewById(R.id.tv_location_bookdetails);
        mTextViewNamaGig = (TextView)findViewById(R.id.tv_nama_gig_bookdetails);
        mTextViewHarga = (TextView)findViewById(R.id.tv_harga_bookdetails);
        mTextViewWaktuMulai = (TextView)findViewById(R.id.tv_waktu_mulai_bookdetails);
        mTextViewWaktuSelesai = (TextView)findViewById(R.id.tv_waktu_selesai_bookdetails);
        mTextViewTotal = (TextView)findViewById(R.id.tv_total_bookdetails);

        mButtonConfirmPayment = (Button)findViewById(R.id.btn_konfirmasi_pembayaran_bookingdetails);

        mSewaId = intent.getIntExtra("sewa_id",0);

        mTextViewNamaMusisi.setText(intent.getStringExtra("nama_grupband"));
        mTextViewNamaGig.setText(intent.getStringExtra("nama_gig"));
        mTextViewUserName.setText(intent.getStringExtra("nama_user"));
        mTextViewLocation.setText(intent.getStringExtra("lokasi"));
        mTextViewWaktuMulai.setText(intent.getStringExtra("waktu_mulai"));
        mTextViewWaktuSelesai.setText(intent.getStringExtra("waktu_selesai"));
        mTextViewTotal.setText("Rp. "+Integer.toString(intent.getIntExtra("total",0)));
        if (intent.getIntExtra("harga_sewa",0)!=0){
            mTextViewHarga.setText("Rp."+Integer.toString(intent.getIntExtra("harga_sewa",0)));
        }
        else if (intent.getIntExtra("harga_sewa",0)==0){
            mTextViewHarga.setText("Rp."+Integer.toString(intent.getIntExtra("harga",0)));

        }

        mButtonConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),KonfirmasiPembayaranActivity.class);
                intent.putExtra("sewa_id",mSewaId);
                startActivity(intent);
            }
        });

    }
}
