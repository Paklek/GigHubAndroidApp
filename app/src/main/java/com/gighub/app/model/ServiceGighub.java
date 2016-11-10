package com.gighub.app.model;

import com.gighub.app.util.StaticString;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST(StaticString.ROUTE_MUSICIAN_GENRES)
    Call<MusicianGenresResponse> sendGenreMusicianData(@Body Map<String, String> genreData);

//
//    @POST(StaticString.ROUTE_LOGIN_MUSICIAN)
//    Call<MResponse> sendLogin(@Body Map<String,String> loginData);
//
    @POST(StaticString.ROUTE_MUSICIAN_BANK)
    Call<BankResponse> sendDataForBank(@Body Map<String,String> bankProfileData);

    @POST(StaticString.ROUTE_UPDATE_MUSICIAN_BANK)
    Call<BankResponse> sendDataForUpdateBank(@Body Map<String,String> updateBankProfileData);

    @POST(StaticString.ROUTE_UPDATE_PROFILE)
    Call<ResponseMusician> sendProfileUpdateDataMusician(@Body Map<String, String> profileUpdateData);

    @POST(StaticString.ROUTE_UPDATE_PROFILE)
    Call<ResponseUser> sendProfileUpdateDataOrganizer(@Body Map<String, String> profileUpdateData);

    @GET(StaticString.ROUTE_ALL_GENRES)
    Call<ResponseCallGenre> loadMusicianGenre();

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchMusician(@Query("kota") String kota);

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchMusicianByKotaNTipe(@Query("kota") String kota,@Query("tipe") String tipe);

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchMusicianByKotaNGenre(@Query("kota") String kota,@Query("genre_name") String genre_name);

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchMusicianByKotaNTipeNGenre(@Query("kota") String kota,@Query("tipe") String tipe,@Query("genre_name") String genre_name);

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchMusicianByRole(@Query("tipe") String tipe);

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchMusicianByGenre(@Query("genre_name") String genre_name);

    @GET(StaticString.ROUTE_SEARCH_MUSICIANS)
    Call<SearchResultResponse> getSearchAllMusician();

    @POST(StaticString.ROUTE_CREATE_MUSICIAN_BAND)
    Call<GrupBandResponse> sendInsertDataBand(@Body Map<String, String> insertDataBand);

    @POST(StaticString.ROUTE_YOUR_BANDS)
    Call<YourBandResponse> sendYourBands(@Body Map<String, String> sendDataYourBands);

    @POST(StaticString.ROUTE_CREATE_GIG)
    Call<GigResponse> sendInsertDataGig(@Body Map<String,String> DataGig);

    @POST(StaticString.ROUTE_SEND_BOOK_GROUPBAND)
    Call<Response> sendBookData(@Body Map<String,String> dataBook);

    @POST(StaticString.ROUTE_LIST_GIG_OFFER_MUSICIAN)
    Call<GigOfferMusicianResponse> sendGigOfferData(@Body Map<String, String> dataMusicianOffer);

    @POST(StaticString.ROUTE_GIG_OFFER)
    Call<Response> sendOfferSubmitData(@Body Map<String, String> submitData);

    @POST(StaticString.ROUTE_CONFIRM_BOOK_REQUEST)
    Call<Response> sendConfirmRequestData (@Body Map<String,String> dataConfirmRequest);

    @POST(StaticString.ROUTE_VIEW_ADD_MUSICIAN_TO_GROUP)
    Call<MResponse> sendForViewMember (@Body Map<String,String> dataViewMember);

    @POST(StaticString.ROUTE_ON_REQUEST_BOOK)
    Call<PenyewaanResponse> sendIdUserForBook(@Body Map<String, String> idUser);

    @POST(StaticString.ROUTE_ON_PROCCESS_BOOK)
    Call<PenyewaanResponse> sendForOnProccessBook(@Body Map<String, String> forOnProccess);

    @POST(StaticString.ROUTE_COMPLETED_BOOK)
    Call<PenyewaanResponse> sendCompletedBook(@Body Map<String, String> dataCompletedBook);

    @POST(StaticString.ROUTE_KONFIRMASI_PEMBAYARAN)
    Call<KonfirmasiPembayaranResponse> sendDataKonfirmasiPembayaran(@Body Map<String, String> dataKonfirmasiPembayaran);

    @GET(StaticString.ROUTE_POSITION)
    Call<PositionResponse> callPosition();

    @POST(StaticString.ROUTE_ADD_ANGGOTA)
    Call<Response> sendAddPositionData(@Body Map<String, String> dataAddPosition);
}
