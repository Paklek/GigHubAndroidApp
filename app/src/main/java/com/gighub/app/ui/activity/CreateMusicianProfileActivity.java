package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gighub.app.R;
import com.gighub.app.ui.fragment.CompletedBookingListFragment;

public class CreateMusicianProfileActivity extends AppCompatActivity {

    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_musician);

        btn_next = (Button)findViewById(R.id.btn_nextCreateMusician);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompleteMusicianProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
