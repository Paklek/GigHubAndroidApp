package com.gighub.app.model;

import com.gighub.app.util.StaticString;

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
import retrofit2.http.Query;

/**
 * Created by Paklek on 7/30/2016.
 */
public interface ServiceGighub {
    @GET("users")
    Call<UserResponse> loadUser();

    @GET(StaticString.ROUTE_ALL_GIGS)
    Call<GigResponse> loadGig();

    @POST(StaticString.ROUTE_YOUR_GIG)
    Call<YourGigResponse> loadYourGig(@Body Map<String, String> organizerId);

    @GET(StaticString.ROUTE_ALL_MUSICIANS)
    Call<MusicianResponse> loadMusicians();

    @POST(StaticString.ROUTE_REGISTER_MUSICIAN)
    Call <Response> insertMusician(@Body Map<String, String> dataMusisi);

    @POST(StaticString.ROUTE_REGISTER_ORGANIZER)
    Call<Response> insertOrganizer(@Body Map<String, String> dataOrganizer);

    @POST(StaticString.ROUTE_LOGIN_ORGANIZER)
    Call<ResponseUser> sendLoginDataOrganizer(@Body Map<String, String> loginData);

    @POST(StaticString.ROUTE_LOGIN_MUSICIAN)
    Call<ResponseMusician> sendLoginDataMusician(@Body Map<String,String> loginData);

//
    @POST(StaticString.ROUTE_LOGIN_MUSICIAN)
    Call<MResponse> sendLogin(@Body Map<String,String> loginData);
//

    @POST(StaticString.ROUTE_UPDATE_PROFILE)
    Call<ResponseMusician> sendProfileUpdateDataMusician(@Body Map<String, String> profileUpdateData);

    @POST(StaticString.ROUTE_UPDATE_PROFILE)
    Call<ResponseUser> sendProfileUpdateDataOrganizer(@Body Map<String, String> profileUpdateData);

    @GET(StaticString.ROUTE_ALL_GENRES)
    Call<ResponseCallGenre> loadMusicianGenre();

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchMusician(@Query("kota") String kota);

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchMusicianByRole(@Query("tipe") String tipe);

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchMusicianByGenre(@Query("genre_name") String tipe);

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchAllMusician();

    @POST(StaticString.ROUTE_CREATE_MUSICIAN_BAND)
    Call<GrupBandResponse> sendInsertDataBand(@Body Map<String, String> insertDataBand);

    @GET(StaticString.ROUTE_BANDS)
    Call<GroupBandsResponse> getDataBand();

    @POST(StaticString.ROUTE_CREATE_GIG)
    Call<GigResponse> sendInsertDataGig(@Body Map<String,String> DataGig);

    @POST(StaticString.ROUTE_SEND_BOOK_GROUPBAND)
    Call<Response> sendBookData(@Body Map<String,String> dataBook);

    @POST(StaticString.ROUTE_ON_REQUEST_BOOK)
    Call<PenyewaanResponse> sendIdUserForBook(@Body Map<String, String> idUser);

    @POST(StaticString.ROUTE_ON_PROCCESS_BOOK)
    Call<PenyewaanResponse> sendForOnProccessBook(@Body Map<String, String> forOnProccess);

    @POST(StaticString.ROUTE_COMPLETED_BOOK)
    Call<PenyewaanResponse> sendCompletedBook(@Body Map<String, String> dataCompletedBook);

    @POST(StaticString.ROUTE_KONFIRMASI_PEMBAYARAN)
    Call<KonfirmasiPembayaranResponse> sendDataKonfirmasiPembayaran(@Body Map<String, String> dataKonfirmasiPembayaran);

}
