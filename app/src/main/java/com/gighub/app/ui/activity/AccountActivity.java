package com.gighub.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Bank;
import com.gighub.app.model.BankResponse;
import com.gighub.app.model.Genre;
import com.gighub.app.model.MusicianSaldo;
import com.gighub.app.model.MusicianSaldoResponse;
import com.gighub.app.model.ResponseCallGenre;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.StaticFunction;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

public class AccountActivity extends AppCompatActivity {

    private Button mButtonGigMoney, mButtonProfile, mButtonAboutUs,mButtonLogout;
    private SessionManager mSession;
    private View mViewButtonLogout, mViewButtonProfile, mViewButtonGigMoney, mViewButtonManager;
    private String mName;
    private Context mContext;
    private List<Genre> mMusicianGenres;
    private List<MusicianSaldo> musicianSaldos;
    private Bank mBank;
    private int mMusicianId, mOrganizerId;

    public static final String PESANLOG ="pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mSession = new SessionManager(getApplicationContext());

        mMusicianGenres = new ArrayList<Genre>();
        musicianSaldos = new ArrayList<MusicianSaldo>();
        mContext = getApplicationContext();



        Intent intent = getIntent();
        final Type type = new TypeToken<List<Genre>>(){}.getType();
        mMusicianGenres = new Gson().fromJson(intent.getStringExtra("musiciangenres"),type);

        if(mSession.isLoggedIn()){
            if(mSession.checkUserType().equals("org")){
                mOrganizerId = mSession.getUserDetails().getId();
                mName = mSession.getUserDetails().getFirst_name();
            }
            else if(mSession.checkUserType().equals("msc")){
                mMusicianId = mSession.getMusicianDetails().getId();
                mName = mSession.getMusicianDetails().getName();
            }

        }


        mViewButtonLogout = (View)findViewById(R.id.btn_logout);
        mViewButtonProfile = (View)findViewById(R.id.btn_profile);
        mViewButtonGigMoney = (View)findViewById(R.id.btn_gig_money);
        mViewButtonManager = (View)findViewById(R.id.btn_manager);

        mButtonProfile = (Button)findViewById(R.id.btn_profile) ;
        mButtonGigMoney = (Button)findViewById(R.id.btn_gig_money);
        mButtonAboutUs = (Button)findViewById(R.id.btn_about_us);
        mButtonLogout = (Button)findViewById(R.id.btn_logout);



        final Map<String,String> dataBank = new HashMap<>();
        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent _intent = new Intent(getApplicationContext(),ProfileActivity.class);


                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();



                buildUrl.serviceGighub.loadMusicianGenre().enqueue(new Callback<ResponseCallGenre>() {
                    @Override
                    public void onResponse(Call<ResponseCallGenre> call, Response<ResponseCallGenre> response) {
                        if (response.code()==200) {
                            _intent.putExtra("genres", new Gson().toJson(response.body().getGenreList()));
                            _intent.putExtra("musiciangenres",new Gson().toJson(mMusicianGenres));
                            Log.d("response", "call genres");
                            StaticFunction staticFunction = new StaticFunction();
                            staticFunction.buildProgressDialog(AccountActivity.this);
                            startActivity(_intent);
                        }
                        else {
                            Log.d("response",Integer.toString(response.code()));
                            Toast.makeText(AccountActivity.this,response.message(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseCallGenre> call, Throwable t) {
                        Log.d("response",t.getCause().getLocalizedMessage());
                    }
                });


            }
        });

        mButtonGigMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildUrl buildUrl = new BuildUrl();
                buildUrl.buildBaseUrl();
                Map<String, String> dataInfoSaldo = new HashMap<String, String>();

                dataInfoSaldo.put("user_id",Integer.toString(mSession.getMusicianDetails().getId()));

                buildUrl.serviceGighub.sendSaldoInfo(dataInfoSaldo).enqueue(new Callback<MusicianSaldoResponse>() {
                    @Override
                    public void onResponse(Call<MusicianSaldoResponse> call, Response<MusicianSaldoResponse> response) {
                        Intent _intent = new Intent(getApplicationContext(),GigMoneyActivity.class);
                        _intent.putExtra("musiciansaldos",new Gson().toJson(response.body().getMusicianSaldos()));
                        startActivity(_intent);
                    }

                    @Override
                    public void onFailure(Call<MusicianSaldoResponse> call, Throwable t) {

                    }
                });


            }
        });
        

        mButtonAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(_intent);
            }
        });

        if(!mSession.isLoggedIn()){
//            View view = (View)findViewById(R.id.btn_logout);
            mButtonLogout.setVisibility(mViewButtonLogout.GONE);
            mButtonProfile.setVisibility(mViewButtonProfile.GONE);
            mViewButtonGigMoney.setVisibility(mViewButtonGigMoney.GONE);
            mViewButtonManager.setVisibility(mViewButtonManager.GONE);
        }
        if (mSession.checkUserType().equals("org")){
            mViewButtonGigMoney.setVisibility(View.GONE);
            mViewButtonManager.setVisibility(View.GONE);
        }
        else if(mSession.checkUserType().equals("msc")){
            mButtonGigMoney.setVisibility(View.VISIBLE);
        }

        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){

        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        Map<String, String> dataLogout = new HashMap<String, String>();

        if(mSession.checkUserType().equals("msc")){
            dataLogout.put("tipe_user","msc");
            dataLogout.put("user_id",Integer.toString(mMusicianId));
        }
        else{
            dataLogout.put("tipe_user","org");
            dataLogout.put("user_id",Integer.toString(mOrganizerId));

        }
        buildUrl.serviceGighub.sendLogoutData(dataLogout).enqueue(new Callback<com.gighub.app.model.Response>() {
            @Override
            public void onResponse(Call<com.gighub.app.model.Response> call, Response<com.gighub.app.model.Response> response) {
                Intent _intent = new Intent(getApplicationContext(),MainActivity.class);
                _intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Log.d(PESANLOG,"Logout Success");
                Toast.makeText(AccountActivity.this,"Logout Success",Toast.LENGTH_LONG).show();
                startActivity(_intent);
                mSession.clearLoginSession();
            }

            @Override
            public void onFailure(Call<com.gighub.app.model.Response> call, Throwable t) {

            }
        });



    }
}
