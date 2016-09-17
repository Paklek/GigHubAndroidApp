package com.gighub.app.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.UserModel;
import com.gighub.app.ui.activity.IntroActivity;
import com.gighub.app.ui.activity.LoginAsActivity;
import com.gighub.app.ui.activity.MainActivity;
import com.google.gson.Gson;

/**
 * Created by Paklek on 6/10/2016.
 */
public class SessionManager {
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    public final static String KEY_PREF_NAME = "PREF_NAME";
    public final static String KEY_PREF_SKIP_INTRO = "PREF_SKIP";
    public final static String KEY_PREF_IS_LOGIN = "PREF_IS_LOGIN";
    public final static String KEY_PREF_USER_DATA = "PREF_USERDATA";
    public final static String KEY_PREF_USER_TYPE = "PREF_USERTYPE";
    public final static String PESANLOG = "pesanlog";

    public SessionManager(Context mContext )
    {
        this.mContext = mContext;
        this.mPreferences = this.mContext.getSharedPreferences(KEY_PREF_NAME,0);
        this.mEditor = this.mPreferences.edit();
    }

    public void SkipIntro()
    {
        mEditor.putString(KEY_PREF_SKIP_INTRO,"true");
        mEditor.commit();
    }
    public boolean isSkipIntroStatus()
    {

        if(mPreferences.getString(KEY_PREF_SKIP_INTRO,"false").equals("true"))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void getSkipIntroStatus(){
        if(!isSkipIntroStatus()) {
            Intent intent = new Intent(mContext, IntroActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mContext.startActivity(intent);
        }
    }

    public void createLoginSession(String DATA, String userType){
        mEditor.putBoolean(KEY_PREF_IS_LOGIN,true);
        mEditor.putString(KEY_PREF_USER_DATA, DATA);
        mEditor.putString(KEY_PREF_USER_TYPE,userType);
        mEditor.commit();
    }

    public boolean isLoggedIn(){
        return mPreferences.getBoolean(KEY_PREF_IS_LOGIN,false);
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(mContext, LoginAsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mContext.startActivity(intent);
        }
    }

    public UserModel getUserDetails(){
        UserModel tmp = new Gson().fromJson(mPreferences.getString(KEY_PREF_USER_DATA,null),UserModel.class);
        return tmp;
    }
    public MusicianModel getMusicianDetails(){
        MusicianModel tmp = new Gson().fromJson(mPreferences.getString(KEY_PREF_USER_DATA,null),MusicianModel.class);
        Log.d(PESANLOG,mPreferences.getString(KEY_PREF_USER_DATA,null));
        return tmp;
    }

    public void clearLoginSession(){
        mEditor.clear();
        mEditor.commit();
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }

    public String checkUserType(){
        return mPreferences.getString(KEY_PREF_USER_TYPE,"");
    }

}

