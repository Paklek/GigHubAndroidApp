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
                .baseUrl(StaticString.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceGighub = retrofit.create(ServiceGighub.class);
    }
}
