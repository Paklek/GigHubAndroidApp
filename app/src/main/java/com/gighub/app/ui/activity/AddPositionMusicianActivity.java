package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.Position;
import com.gighub.app.ui.adapter.ListAddPositionMusicianAdapter;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddPositionMusicianActivity extends AppCompatActivity {

    private GridView mGridView;
    private TextView mTextViewName, mTextViewBasis, mTextViewKota;
    private List<Position> mPositionList;
    private SessionManager mSession;
    private ListAddPositionMusicianAdapter listAddPositionMusicianAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_position_musician);

        mPositionList = new ArrayList<Position>();

        mSession = new SessionManager(getApplicationContext());
        mContext = getApplicationContext();

        final Intent intent = getIntent();
        final Type type = new TypeToken<List<Position>>(){}.getType();

        mPositionList = new Gson().fromJson(intent.getStringExtra("positions"),type);

        mGridView = (GridView)findViewById(R.id.gv_position_addpositionmusician);
        mTextViewName = (TextView)findViewById(R.id.tv_name_addpostitionmusician);
        mTextViewBasis = (TextView)findViewById(R.id.tv_basis_addpostitionmusician);
        mTextViewKota = (TextView)findViewById(R.id.tv_kota_addpostitionmusician);

//        listAddPositionMusicianAdapter = new ListAddPositionMusicianAdapter(getApplicationContext(),mPositionList);
        mTextViewName.setText(intent.getStringExtra("name"));
        mTextViewKota.setText(intent.getStringExtra("kota"));
        mTextViewBasis.setText(intent.getStringExtra("basis"));

        mGridView.setAdapter(new ListAddPositionMusicianAdapter(mContext,mPositionList));
    }
}
