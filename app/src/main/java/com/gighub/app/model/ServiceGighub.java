package com.gighub.app.model;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Paklek on 7/30/2016.
 */
public interface ServiceGighub {
    @GET("users")
    Call<UserResponse> loadUser();

    @GET("musicians")
    Call<MusicianResponse> loadMusicians();

    @POST("api/musician/register")
    Call <Response> insertMusician(@Body Map<String, String> dataMusisi);

    @POST("api/organizer/register")
    Call<Response> insertOrganizer(@Body Map<String, String> dataOrganizer);

    @POST("api/organizer/login")
    Call<ResponseUser> sendLoginDataOrganizer(@Body Map<String, String> loginData);

    @POST("api/musician/login")
    Call<ResponseUser> sendLoginDataMusician(@Body Map<String,String> loginData);
}
