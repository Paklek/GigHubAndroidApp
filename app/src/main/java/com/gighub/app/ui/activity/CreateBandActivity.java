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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.GrupBandResponse;
import com.gighub.app.model.Position;
import com.gighub.app.model.PositionResponse;
import com.gighub.app.ui.adapter.ListGenreCreateBandAdapter;
import com.gighub.app.ui.adapter.ListPositionAdapter;
import com.gighub.app.ui.fragment.DialogGenreFragment;
import com.gighub.app.ui.fragment.DialogPositionCreatBandFragment;
import com.gighub.app.ui.fragment.DiscoverMusicianFragment;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBandActivity extends AppCompatActivity {

    private EditText mEditTextNamaBand, mEditTextDeskripsiBand, mEditTextHargaBand, mEditTextKota,mEditTextSelectGenres, mEditTextSelectPosition;
    private Button mButtonCreateBand;
    private CheckBox mCheckBoxGenrePop, mCheckBoxGenreRock;
    private SessionManager mSession;
    private String mMusicianId,mPosition, mGenreDipilih ="";
    private Context mContext;
    private Spinner mSpinnerPosition;
    private CheckBox[] cbxs = new CheckBox[5];
    private LinearLayout mLinearLayoutCheckBoxGenre;
    private String[] genres= new String[5];
    private GridView mGridView;
    private List<Genre> mGenres;



    private int mPositionId,mCount=0;

    private String[] mListPosition={"Position","Vocalist","Guitarist","Bassist","Drummer","Keyboardist"};

    public static final String CREATEBAND = "createband";
    public static final int REQQODE = 1000;

    DialogFragment dialogFragment;
    private ListGenreCreateBandAdapter listGenreCreateBandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_band);
        mContext = getApplicationContext();

        final Activity activity = this;

        mGenres = new ArrayList<Genre>();

        mSession = new SessionManager(getApplicationContext());

        mGridView = (GridView)findViewById(R.id.lv_genres_createband);
        mLinearLayoutCheckBoxGenre = (LinearLayout)findViewById(R.id.ll_list_genre);
        mEditTextSelectPosition = (EditText)findViewById(R.id.et_selectposition_createband);

        final Intent intent = getIntent();
        final Type type = new TypeToken<List<Genre>>(){}.getType();
        mGenres = new Gson().fromJson(intent.getStringExtra("genres"),type);

        listGenreCreateBandAdapter = new ListGenreCreateBandAdapter(mContext,mGenres);

        mGridView.setAdapter(listGenreCreateBandAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CreateBandActivity.this,mGenres.get(position).getGenre_name(),Toast.LENGTH_SHORT).show();
                Log.d("onItemClick",""+mGenres.get(position).getGenre_name());
//                mGridView.getChildAt(position);
//                Log.d("isSelected",""+parent.getChildAt(position).isSelected());
//                Log.d("isPressed",""+parent.getChildAt(position).isPressed());
//                Log.d("isSelected",""+parent.getChildAt(position).is);
                Log.d("isSelected",""+mGridView.getChildAt(position).isSelected());
                Log.d("isPressed",""+mGridView.getChildAt(position).isPressed());
                Log.d("isSelected",""+mGridView.isItemChecked(position));

//                Log.d("isSelected",""+selected[position]);
                if (!mGenres.get(position).getSelected()){
                    mGridView.getChildAt(position).setBackground(mGridView.getResources().getDrawable(R.drawable.button_border_black));
                    mGenres.get(position).setSelected(true);
                    mGenreDipilih += " "+mGenres.get(position).getId();
                    mCount +=1;
                }
                else
                {
                    mGridView.getChildAt(position).setBackground(mGridView.getResources().getDrawable(R.drawable.spinner_drawable));
                    mGenres.get(position).setSelected(false);
                    mGenreDipilih = mGenreDipilih.replace(" "+mGenres.get(position).getId(),"");
                    mCount -= 1;

                }

            }
        });

//        for(int i=0;i<cbxs.length;i++){
//            cbxs[i] = new CheckBox(mContext);
//            if(i==0){
//                cbxs[i].setText("Pop");
//            }
//            if(i==1){
//                cbxs[i].setText("Rock");
//            }
//            if(i==2){
//                cbxs[i].setText("Jazz");
//            }
//            if(i==3){
//                cbxs[i].setText("Dangdut");
//            }
//            if(i==4){
//                cbxs[i].setText("Reggeae");
//            }
//            cbxs[i].setPadding(10,10,10,10);
//            cbxs[i].setButtonDrawable(this.getResources().getDrawable(R.drawable.custom_checkbox));
//            cbxs[i].setTextColor(this.getResources().getColor(R.color.colorTextDark));
//            mLinearLayoutCheckBoxGenre.addView(cbxs[i]);
//        }

        mEditTextNamaBand = (EditText)findViewById(R.id.et_nama_band_createband);
        mEditTextDeskripsiBand = (EditText)findViewById(R.id.et_band_descriptions_createband);
        mEditTextHargaBand = (EditText)findViewById(R.id.et_harga_sewa_createband);
        mEditTextKota = (EditText)findViewById(R.id.spinner_kota_band_createband);

//        mSpinnerPosition = (Spinner)findViewById(R.id.spinner_position_createbandactivity);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.spinner_item_position,mListPosition);
//        adapter.setDropDownViewResource(R.layout.spinner_item_position);
//        mSpinnerPosition.setAdapter(adapter);
//
        mEditTextSelectPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();

                buildUrl.serviceGighub.callPosition().enqueue(new Callback<PositionResponse>() {
                    @Override
                    public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
//                        Intent i = new Intent(mContext,DialogPositionCreatBandFragment.class);
                        dialogFragment = new DialogPositionCreatBandFragment();
                        dialogFragment.show(getSupportFragmentManager(),"position");

//                        Bundle args = new Bundle();
                    }

                    @Override
                    public void onFailure(Call<PositionResponse> call, Throwable t) {

                    }
                });

            }
        });

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

//
//        cbxs[0].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(0);
//            }
//        });
//        cbxs[1].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(1);
//            }
//        });
//        cbxs[2].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(2);
//            }
//        });
//        cbxs[3].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(3);
//            }
//        });
//        cbxs[4].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertGenre(4);
//            }
//        });


//        mSpinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mPosition = mSpinnerPosition.getAdapter().getItem(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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

//        if (mPosition.equals("Vocalist")){
//            mPositionId = 1;
//        }
//        else  if(mPosition.equals("Guitarist")){
//            mPositionId = 2;
//        }
//        else if(mPosition.equals("Bassist")){
//            mPositionId = 3;
//        }
//        else if(mPosition.equals("Drummer")){
//            mPositionId = 4;
//        }
//        else if(mPosition.equals("Keyboardist")){
//            mPositionId = 5;
//        }
//        else mPosition="Position";




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
        Log.d("posisi",""+mPositionId);
        Log.d("genre_count",""+mCount);

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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==CreateBandActivity.REQQODE){
//            List<Genre> genreList = new ArrayList<>();
//            Type typeGenreList = new TypeToken<List<Genre>>(){}.getType();
//            genreList = new Gson().fromJson(data.getStringExtra("kirim"),typeGenreList);
//            String genres = "";
//            for(Genre g:genreList) {
//                if(g.getSelected()){
//                    genres += g.getGenre_name()+" ";
//                }
//            }
//            mEditTextSelectGenres.setText(genres);
////            for(int i=0;i)
//        }
//    }

//    private void insertGenre (int checkBoxIndex){
//        if(cbxs[checkBoxIndex].isChecked()) {
//            Log.d("genre", cbxs[checkBoxIndex].getText().toString());
//            genres[checkBoxIndex] = " "+(checkBoxIndex+1);
//            mGenreDipilih += genres[checkBoxIndex];
//            mCount+=1;
//        }
//        else{
//            mGenreDipilih = mGenreDipilih.replace(" "+(checkBoxIndex+1),"");
//            mCount-=1;
//        }
//        Log.d("genreyangdipilih",mGenreDipilih);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== ListPositionAdapter.REQ_POSITION_CODE) {
            List<Position> positionList = new ArrayList<Position>();
            Type typePositionList = new TypeToken<List<Position>>() {
            }.getType();
            positionList = new Gson().fromJson(data.getStringExtra("posisi"), typePositionList);
            String positions = "";
            int idPosition=0;
//            for(Position position:positionList) {
//                if(position.isSelected()){
////                    if(!genres.equals("")){
////                        genres += ","+g.getGenre_name();
////                    }
////                    else {
////                        genres += g.getGenre_name();
////                    }
////                    idPosition = position.getId();
//                    positions +=""+position.getPosition_name();
//                    idPosition += ""+Integer.toString(position.getId());
//                }
//            }
//            for(int i=0;i<positionList.size();i++){
//                if(positionList.get(i).isSelected()){
//                    positions +=""+positionList.get(i).getPosition_name();
//                    idPosition += positionList.get(i).getId();
//                }
//            }
//
////            mPositionId = idPosition;
//            mEditTextSelectPosition.setText(positions+" Ok");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onGetData(Position position){
        Log.d("Data Received",position.getPosition_name());
        mEditTextSelectPosition.setText(position.getPosition_name());
        mPositionId = position.getId();
    }
}
