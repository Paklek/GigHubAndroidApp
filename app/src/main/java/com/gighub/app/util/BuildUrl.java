package com.gighub.app.util;

import com.gighub.app.model.ServiceGighub;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 02/09/2016.
 */
public class BuildUrl {
    public ServiceGighub serviceGighub;
    public void buildBaseUrl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.108.49.230/Gighub-master/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceGighub = retrofit.create(ServiceGighub.class);
    }
}
