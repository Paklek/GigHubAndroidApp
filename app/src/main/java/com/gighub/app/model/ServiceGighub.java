package com.gighub.app.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Paklek on 7/30/2016.
 */
public interface ServiceGighub {
    @GET("users")
    Call<UserResponse> loadUser();
}
