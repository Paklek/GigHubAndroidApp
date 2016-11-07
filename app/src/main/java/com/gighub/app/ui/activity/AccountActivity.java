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
import com.gighub.app.model.Genre;
import com.gighub.app.model.ResponseCallGenre;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.SessionManager;
import com.gighub.app.util.StaticFunction;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    public static final String PESANLOG ="pesanlog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mSession = new SessionManager(getApplicationContext());

        mMusicianGenres = new ArrayList<Genre>();
        mContext = getApplicationContext();



        Intent intent = getIntent();
        final Type type = new TypeToken<List<Genre>>(){}.getType();
        mMusicianGenres = new Gson().fromJson(intent.getStringExtra("musiciangenres"),type);

        if(mSession.isLoggedIn()){
            if(mSession.checkUserType().equals("org")){
                mName = mSession.getUserDetails().getFirst_name();
            }
            else if(mSession.checkUserType().equals("msc")){
                mName = mSession.getMusicianDetails().getName();
            }

        }


        mViewButtonLogout = (View)findViewById(R.id.btn_logout);
        mViewButtonProfile = (View)findViewById(R.id.btn_profile);
        mViewButtonGigMoney = (View)findViewById(R.id.btn_gig_money);
        mViewButtonManager = (View)findViewById(R.id.btn_manager);

        mButtonProfile = (Button)findViewById(R.id.btn_profile) ;
//        mButtonGigMoney = (Button)findViewById(R.id.btn_gig_money);
        mButtonAboutUs = (Button)findViewById(R.id.btn_about_us);
        mButtonLogout = (Button)findViewById(R.id.btn_logout);


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
                            Toast.makeText(AccountActivity.this,response.code(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseCallGenre> call, Throwable t) {
                        Log.d("response",t.getCause().getLocalizedMessage());
                    }
                });


            }
        });

//        mButtonGigMoney.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent _intent = new Intent(getApplicationContext(),GigMoneyActivity.class);
//                startActivity(_intent);
//            }
//        });
        

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

        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){
        mSession = new SessionManager(getApplicationContext());
        mSession.clearLoginSession();
        Intent _intent = new Intent(getApplicationContext(),MainActivity.class);
        _intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d(PESANLOG,"Logout Success");
        Toast.makeText(AccountActivity.this,"Logout Success",Toast.LENGTH_LONG).show();
        startActivity(_intent);

    }
}
