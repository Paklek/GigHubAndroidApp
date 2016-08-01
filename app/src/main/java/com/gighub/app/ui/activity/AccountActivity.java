package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gighub.app.R;

public class AccountActivity extends AppCompatActivity {

    private Button btn_gigMoney, btn_manager, btn_profile, btn_aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btn_profile = (Button)findViewById(R.id.btn_profile) ;
        btn_gigMoney = (Button)findViewById(R.id.btn_gig_money);
        btn_manager = (Button)findViewById(R.id.btn_manager);
        btn_aboutUs = (Button)findViewById(R.id.btn_about_us);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });

        btn_gigMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GigMoneyActivity.class);
                startActivity(intent);
            }
        });

        btn_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ManagerActivity.class);
                startActivity(intent);
            }
        });

        btn_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
