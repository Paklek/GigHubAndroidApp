package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gighub.app.R;

public class LoginAsActivity extends AppCompatActivity {

    private Button mBtnLoginAsOrg, mBtnLoginAsMsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as);

        mBtnLoginAsOrg = (Button)findViewById(R.id.btn_login_as_org);
        mBtnLoginAsMsc = (Button)findViewById(R.id.btn_login_as_msc);

        mBtnLoginAsOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginAsOrganizerActivity.class);
                startActivity(intent);
            }
        });

        mBtnLoginAsMsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginAsMusicianActivity.class);
                startActivity(intent);
            }
        });
    }
}
