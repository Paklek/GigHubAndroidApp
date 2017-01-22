package com.gighub.app.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 13/01/2017.
 */
public interface DistanceGig {

    /*APA ISINYA WAK?*/

//    @GET("api/directions/json?key=AIzaSyCnr-Sxlgu9soj-V9u4xaoP1kDEy3ULW3A")
    @GET("api/directions/json")
    Call<ResponseDistance> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);
}
