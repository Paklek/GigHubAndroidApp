package com.gighub.app.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import com.gighub.app.R;
import com.gighub.app.ui.activity.AccountActivity;
import com.gighub.app.ui.activity.BookingDetailsActivity;
import com.gighub.app.ui.activity.BookingListActivity;
import com.gighub.app.ui.activity.CreateBandActivity;
import com.gighub.app.ui.activity.CreateGigActivity;
import com.gighub.app.ui.activity.GroupBandActivity;
import com.gighub.app.ui.activity.LoginAsActivity;
import com.gighub.app.ui.activity.RegisterAsActivity;
import com.gighub.app.ui.activity.YourGigActivity;

/**
 * Created by user on 28/10/2016.
 */
public class StaticFunction {

    private static Context mContext ;

    public void buildProgressDialog(Context getContext){
        final ProgressDialog dialog = new ProgressDialog(getContext); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new Thread(new Runnable(){
            public void run(){
                try {
                    Thread.sleep(1000);
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void createOptionMenu(Menu menu,SessionManager sessionManager, Class<?> activityIni){
//        Class<?> activityIni;
        if(sessionManager.isLoggedIn()){
            if(sessionManager.checkUserType().equals("org")){
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);
            }
            else if(sessionManager.checkUserType().equals("msc")){
                menu.getItem(2).setVisible(true);
                menu.getItem(3).setVisible(true);
                menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(false);
                if(activityIni==BookingDetailsActivity.class){
                    menu.getItem(6).setVisible(false);
                }
            }
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        if(!sessionManager.isLoggedIn()){
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
            menu.getItem(4).setVisible(false);
            menu.getItem(5).setVisible(false);
            menu.getItem(6).setVisible(false);
        }
    }
    public static StaticFunction get(Context context){
        mContext = context;
        return new StaticFunction();
    }
    public  String getRealBaseURL(Uri uri){
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor =    mContext.getContentResolver().query(uri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
