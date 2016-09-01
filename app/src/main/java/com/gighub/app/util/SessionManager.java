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
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public final static String KEY_PREF_NAME = "PREF_NAME";
    public final static String KEY_PREF_SKIP_INTRO = "PREF_SKIP";
    public final static String KEY_PREF_IS_LOGIN = "PREF_IS_LOGIN";
    public final static String KEY_PREF_USER_DATA = "PREF_USERDATA";
    public final static String KEY_PREF_USER_TYPE = "PREF_USERTYPE";
    public final static String PESANLOG = "pesanlog";

    public SessionManager(Context context )
    {
        this.context = context;
        this.preferences = this.context.getSharedPreferences(KEY_PREF_NAME,0);
        this.editor = this.preferences.edit();
    }

    public void SkipIntro()
    {
        editor.putString(KEY_PREF_SKIP_INTRO,"true");
        editor.commit();
    }
    public boolean isSkipIntroStatus()
    {

        if(preferences.getString(KEY_PREF_SKIP_INTRO,"false").equals("true"))
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
            Intent intent = new Intent(context, IntroActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }

    public void createLoginSession(String DATA, String userType){
        editor.putBoolean(KEY_PREF_IS_LOGIN,true);
        editor.putString(KEY_PREF_USER_DATA, DATA);
        editor.putString(KEY_PREF_USER_TYPE,userType);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(KEY_PREF_IS_LOGIN,false);
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(context, LoginAsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }

    public UserModel getUserDetails(){
        UserModel tmp = new Gson().fromJson(preferences.getString(KEY_PREF_USER_DATA,null),UserModel.class);
        return tmp;
    }
    public MusicianModel getMusicianDetails(){
        MusicianModel tmp = new Gson().fromJson(preferences.getString(KEY_PREF_USER_DATA,null),MusicianModel.class);
        Log.d(PESANLOG,preferences.getString(KEY_PREF_USER_DATA,null));
        return tmp;
    }

    public void clearLoginSession(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public String checkUserType(){
        return preferences.getString(KEY_PREF_USER_TYPE,"");
    }

}

