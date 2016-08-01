package com.gighub.app.model;

import com.gighub.app.util.StaticString;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paklek on 7/31/2016.
 */
public class RetrofitService {
    private static RetrofitService instance;
    private static ServiceGighub api;
    private static Retrofit retrofit;
    public static RetrofitService getInstance() {
        if(instance==null){
            instance = new RetrofitService();
        }
        return instance;
    }
    public ServiceGighub getApi(){
        return api;
    }
    private RetrofitService() {
        retrofit =  new Retrofit.Builder()
                .baseUrl(StaticString.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ServiceGighub.class);
    }
}
