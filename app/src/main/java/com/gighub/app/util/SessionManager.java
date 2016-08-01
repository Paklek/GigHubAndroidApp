package com.gighub.app.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.gighub.app.ui.activity.IntroActivity;

/**
 * Created by Paklek on 6/10/2016.
 */
public class SessionManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public final static String KEY_PREF_NAME = "PREF_NAME";
    public final static String KEY_PREF_SKIP_INTRO = "PREF_SKIP";
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

}

