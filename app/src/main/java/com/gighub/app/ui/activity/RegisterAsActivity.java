package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gighub.app.R;

public class RegisterAsActivity extends AppCompatActivity {

    private Button mButtonRegisterAsOrganizer, mButtinRegisterAsMusician;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

        mButtonRegisterAsOrganizer = (Button)findViewById(R.id.btn_reg_as_org);
        mButtinRegisterAsMusician = (Button)findViewById(R.id.btn_reg_as_msc);

        mButtonRegisterAsOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterAsOrganizerActivity.class);
                startActivity(intent);
            }
        });

        mButtinRegisterAsMusician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterAsMusicianActivity.class);
                startActivity(intent);
            }
        });
    }
}
