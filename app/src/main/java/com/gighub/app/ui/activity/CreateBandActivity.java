package com.gighub.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.GrupBandResponse;
import com.gighub.app.ui.fragment.DialogGenreFragment;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBandActivity extends AppCompatActivity {

    private EditText mEditTextNamaBand, mEditTextDeskripsiBand, mEditTextHargaBand, mEditTextKota,mEditTextSelectGenres;
    private Button mButtonCreateBand;
    private CheckBox mCheckBoxGenrePop, mCheckBoxGenreRock;
    private SessionManager mSession;
    private String mMusicianId,mPosition, mGenreDipilih ="";
    private Context mContext;
    private Spinner mSpinnerPosition;
    private CheckBox[] cbxs = new CheckBox[5];
    private LinearLayout mLinearLayoutCheckBoxGenre;
    private String[] genres= new String[5];


    private int mPositionId,mCount=0;

    private String[] mListPosition={"Position","Vocalist","Guitarist","Bassist","Drummer","Keyboardist"};

    public static final String CREATEBAND = "createband";
    public static final int REQQODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_band);
        mContext = getApplicationContext();

        final Activity activity = this;

        mSession = new SessionManager(getApplicationContext());

        mLinearLayoutCheckBoxGenre = (LinearLayout)findViewById(R.id.ll_list_genre);

        for(int i=0;i<cbxs.length;i++){
            cbxs[i] = new CheckBox(mContext);
            if(i==0){
                cbxs[i].setText("Pop");
            }
            if(i==1){
                cbxs[i].setText("Rock");
            }
            if(i==2){
                cbxs[i].setText("Jazz");
            }
            if(i==3){
                cbxs[i].setText("Dangdut");
            }
            if(i==4){
                cbxs[i].setText("Reggeae");
            }
            cbxs[i].setPadding(10,10,10,10);
            cbxs[i].setButtonDrawable(this.getResources().getDrawable(R.drawable.custom_checkbox));
            cbxs[i].setTextColor(this.getResources().getColor(R.color.colorTextDark));
            mLinearLayoutCheckBoxGenre.addView(cbxs[i]);
        }

        mEditTextNamaBand = (EditText)findViewById(R.id.et_nama_band_createband);
        mEditTextDeskripsiBand = (EditText)findViewById(R.id.et_band_descriptions_createband);
        mEditTextHargaBand = (EditText)findViewById(R.id.et_harga_sewa_createband);
        mEditTextKota = (EditText)findViewById(R.id.spinner_kota_band_createband);

        mSpinnerPosition = (Spinner)findViewById(R.id.spinner_position_createbandactivity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.spinner_item_position,mListPosition);
        adapter.setDropDownViewResource(R.layout.spinner_item_position);
        mSpinnerPosition.setAdapter(adapter);

//        mCheckBoxGenrePop = (CheckBox)findViewById(R.id.cbx_genre_pop_createbandactivity);
//        mCheckBoxGenreRock = (CheckBox)findViewById(R.id.cbx_genre_rock_createbandactivity);

//        mEditTextSelectGenres = (EditText)findViewById(R.id.et_select_genre_createbandactivity);
//        mEditTextSelectGenres.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment dialogFragment = new DialogGenreFragment();
//                dialogFragment.show(getSupportFragmentManager(),Integer.toString(REQQODE));
//            }
//        });


        cbxs[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGenre(0);
            }
        });
        cbxs[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGenre(1);
            }
        });
        cbxs[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGenre(2);
            }
        });
        cbxs[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGenre(3);
            }
        });
        cbxs[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGenre(4);
            }
        });


        mSpinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition = mSpinnerPosition.getAdapter().getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mMusicianId = Integer.toString(mSession.getMusicianDetails().getId());

        mButtonCreateBand = (Button)findViewById(R.id.btn_create_band_createband);

        mButtonCreateBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBand();
            }
        });

    }

    Map<String,String> dataBand = new HashMap<>();
//    Map<String,String[]> dataArray = new HashMap<>();


    private void insertBand(){

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        if (mPosition.equals("Vocalist")){
            mPositionId = 1;
        }
        else  if(mPosition.equals("Guitarist")){
            mPositionId = 2;
        }
        else if(mPosition.equals("Bassist")){
            mPositionId = 3;
        }
        else if(mPosition.equals("Drummer")){
            mPositionId = 4;
        }
        else if(mPosition.equals("Keyboardist")){
            mPositionId = 5;
        }
        else mPosition="Position";


        mGenreDipilih = mGenreDipilih.substring(1,mGenreDipilih.length());
        Log.d("genreyangdikirim",mGenreDipilih);

        dataBand.put("genre_count",Integer.toString(mCount));
        dataBand.put("genre_id",mGenreDipilih);
        dataBand.put("admin_id",mMusicianId);
        dataBand.put("nama_grupband",mEditTextNamaBand.getText().toString());
        dataBand.put("harga",mEditTextHargaBand.getText().toString());
        dataBand.put("deskripsi",mEditTextDeskripsiBand.getText().toString());
        dataBand.put("kota", mEditTextKota.getText().toString());
        dataBand.put("posisi", Integer.toString(mPositionId));


        buildUrl.serviceGighub.sendInsertDataBand(dataBand).enqueue(new Callback<GrupBandResponse>() {
            @Override
            public void onResponse(Call<GrupBandResponse> call, Response<GrupBandResponse> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(mContext,MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(CreateBandActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Log.d(CREATEBAND,"response " +response.code() +" "+ response.body().getMessage());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(intent);
                }
                else {
                    Log.d(CREATEBAND, "Pesan Log : " + response.code() + response.message());
                    Toast.makeText(CreateBandActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
//                Log.d(CREATEBAND,"code" +response.code()+" "+response.message());
            }

            @Override
            public void onFailure(Call<GrupBandResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CreateBandActivity.REQQODE){
            List<Genre> genreList = new ArrayList<>();
            Type typeGenreList = new TypeToken<List<Genre>>(){}.getType();
            genreList = new Gson().fromJson(data.getStringExtra("kirim"),typeGenreList);
            String genres = "";
            for(Genre g:genreList) {
                if(g.getSelected()){
                    genres += g.getGenre_name()+" ";
                }
            }
            mEditTextSelectGenres.setText(genres);
//            for(int i=0;i)
        }
    }

    private void insertGenre (int checkBoxIndex){
        if(cbxs[checkBoxIndex].isChecked()) {
            Log.d("genre", cbxs[checkBoxIndex].getText().toString());
            genres[checkBoxIndex] = " "+(checkBoxIndex+1);
            mGenreDipilih += genres[checkBoxIndex];
            mCount+=1;
        }
        else{
            mGenreDipilih = mGenreDipilih.replace(" "+(checkBoxIndex+1),"");
            mCount-=1;
        }
        Log.d("genreyangdipilih",mGenreDipilih);
    }
}
