package com.gighub.app.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by user on 28/10/2016.
 */
public class StaticFunction {
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
}
