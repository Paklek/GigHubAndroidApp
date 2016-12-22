package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.Position;
import com.gighub.app.model.Response;
import com.gighub.app.ui.adapter.ListAddPositionMusicianAdapter;
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

public class AddPositionMusicianActivity extends AppCompatActivity {

    private GridView mGridView;
    private TextView mTextViewName, mTextViewBasis, mTextViewKota;
    private Button mButtonAdd;
    private List<Position> mPositionList;
    private SessionManager mSession;
    private ListAddPositionMusicianAdapter listAddPositionMusicianAdapter;
    private Context mContext;
//    private SessionManager mSession;
    private int mCalonId,mGrupBandId, mPositionId,mUserId, mAdminId;

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

        mButtonAdd = (Button)findViewById(R.id.btn_add_position_addpositionmusician);
//        listAddPositionMusicianAdapter = new ListAddPositionMusicianAdapter(getApplicationContext(),mPositionList);
        mTextViewName.setText(intent.getStringExtra("name"));
        mTextViewKota.setText(intent.getStringExtra("kota"));
        mTextViewBasis.setText(intent.getStringExtra("basis"));
        mCalonId = intent.getIntExtra("calon_id",0);
        mGrupBandId = intent.getIntExtra("grupband_id",0);
        mUserId = mSession.getMusicianDetails().getId();
        mAdminId = intent.getIntExtra("admin_id",0);

        mGridView.setAdapter(new ListAddPositionMusicianAdapter(mContext,mPositionList));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("item isSelected", mGridView.getChildAt(position).isSelected()+"");
//                Log.d("item isPressed", mGridView.getChildAt(position).isPressed()+"");

//                Log.d("position",mPositionList.get(position).getPosition_name());
                if(!mPositionList.get(position).isSelected()) {
                    for(int p = 0;p<mPositionList.size();p++){
                        mPositionList.get(p).setSelected(false);
                        mGridView.getChildAt(p).setBackground(mGridView.getResources().getDrawable(R.drawable.spinner_drawable));
                    }
                    mPositionList.get(position).setSelected(true);
                    mPositionId = mPositionList.get(position).getId();
                    Log.d("item", mPositionList.get(position).getPosition_name()+" "+mPositionList.get(position).isSelected());
                    mGridView.getChildAt(position).setBackground(mGridView.getResources().getDrawable(R.drawable.button_border_black));
                }
                else {
                    mPositionList.get(position).setSelected(false);
                    Log.d("item", mPositionList.get(position).getPosition_name()+" "+mPositionList.get(position).isSelected());
                    mGridView.getChildAt(position).setBackground(mGridView.getResources().getDrawable(R.drawable.spinner_drawable));
                }
            }
        });

//        if(mAdminId!=mUserId){
//
//        }

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> positionData = new HashMap<String, String>();

                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();

                positionData.put("user_id",Integer.toString(mUserId));
                positionData.put("calon_id",Integer.toString(mCalonId));
                positionData.put("grupband_id",Integer.toString(mGrupBandId));
                positionData.put("position_id",Integer.toString(mPositionId));
                buildUrl.serviceGighub.sendAddPositionData(positionData).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Intent intent1 = new Intent(mContext,MainActivity.class);
                        Log.d("Code", "" + response.code());
                        Toast.makeText(AddPositionMusicianActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
            }
        });

    }
}
