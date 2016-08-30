package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gighub.app.R;

public class JoinAsActivity extends AppCompatActivity {

    private Button btnRegAsOrg, btnRegAsMsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_as);

        btnRegAsOrg = (Button)findViewById(R.id.btn_reg_as_org);
        btnRegAsMsc = (Button)findViewById(R.id.btn_reg_as_msc);

        btnRegAsOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterAsOrganizerActivity.class);
                startActivity(intent);
            }
        });

        btnRegAsMsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterAsMusicianActivity.class);
                startActivity(intent);
            }
        });
    }
}
