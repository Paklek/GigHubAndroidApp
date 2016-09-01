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

    private Button btnGigMoney, btnManager, btnProfile, btnAboutUs,btnLogout;
    private SessionManager session;

    public static final String PESANLOG ="pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btnProfile = (Button)findViewById(R.id.btn_profile) ;
        btnGigMoney = (Button)findViewById(R.id.btn_gig_money);
        btnManager = (Button)findViewById(R.id.btn_manager);
        btnAboutUs = (Button)findViewById(R.id.btn_about_us);
        btnLogout = (Button)findViewById(R.id.btn_logout);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnGigMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GigMoneyActivity.class);
                startActivity(intent);
            }
        });

        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ManagerActivity.class);
                startActivity(intent);
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){
        session = new SessionManager(getApplicationContext());
        session.clearLoginSession();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d(PESANLOG,"Logout Success");
        Toast.makeText(AccountActivity.this,"Logout Success",Toast.LENGTH_LONG).show();
        startActivity(intent);

    }
}
