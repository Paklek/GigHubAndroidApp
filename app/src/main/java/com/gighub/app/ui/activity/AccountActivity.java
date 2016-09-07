package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.util.SessionManager;

public class AccountActivity extends AppCompatActivity {

    private Button mBtnGigMoney, mBtnManager, mBtnProfile, mBtnAboutUs,mBtnLogout;
    private SessionManager mSession;

    public static final String PESANLOG ="pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mBtnProfile = (Button)findViewById(R.id.btn_profile) ;
//        mBtnGigMoney = (Button)findViewById(R.id.btn_gig_money);
//        mBtnManager = (Button)findViewById(R.id.btn_manager);
        mBtnAboutUs = (Button)findViewById(R.id.btn_about_us);
        mBtnLogout = (Button)findViewById(R.id.btn_logout);

        mBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(_intent);
            }
        });

//        mBtnGigMoney.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent _intent = new Intent(getApplicationContext(),GigMoneyActivity.class);
//                startActivity(_intent);
//            }
//        });

//        mBtnManager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent _intent = new Intent(getApplicationContext(),ManagerActivity.class);
//                startActivity(_intent);
//            }
//        });

        mBtnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(_intent);
            }
        });

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){
        mSession = new SessionManager(getApplicationContext());
        mSession.clearLoginSession();
        Intent _intent = new Intent(getApplicationContext(),MainActivity.class);
        _intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d(PESANLOG,"Logout Success");
        Toast.makeText(AccountActivity.this,"Logout Success",Toast.LENGTH_LONG).show();
        startActivity(_intent);

    }
}
