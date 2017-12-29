package com.iu33.driverbangjek.ConfigRetrofit;

import com.iu33.driverbangjek.ResponeGoogle.ResponseRoute;
import com.iu33.driverbangjek.ResponeServer.ResponseCheckBooking;
import com.iu33.driverbangjek.ResponeServer.ResponseDaftar;
import com.iu33.driverbangjek.ResponeServer.ResponseEmail;
import com.iu33.driverbangjek.ResponeServer.ResponseGetBooking;
import com.iu33.driverbangjek.ResponeServer.ResponseTarif;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hp on 12/25/2017.
 */

public interface ApiService {

    @FormUrlEncoded //digunakan untuk Function
    @POST("daftar/4")
    Call<ResponseDaftar> request_daftar(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("token") String token
    );

    @FormUrlEncoded //digunakan untuk Function
    @POST("cekEmail")
    Call<ResponseDaftar> request_email(
            @Field("email") String email
    );

    @GET("json")
    Call<ResponseRoute> requestRoute(@Query("origin")
                                             String origin,
                                     @Query("destination")
                                             String tujuan);

    @FormUrlEncoded
    @POST("insert_booking")
    Call<ResponseTarif> requestTarif(
            @Field("f_idUser") String idUser,
            @Field("f_latAwal") String latAwal,
            @Field("f_lngAwal") String lngAwal,
            @Field("f_awal") String awal,
            @Field("f_latAkhir") String latAkhir,
            @Field("f_lngAkhir") String lngAkhir,
            @Field("f_akhir") String akhir,
            @Field("f_alamat") String alamat,
            @Field("f_jarak") String jarak,
            @Field("f_tarif") String tarifUser
    );

    @FormUrlEncoded
    @POST("cancel_booking")
    Call<ResponseEmail> requestCancel(
            @Field("id_booking") int id_booking
    );

    @FormUrlEncoded
    @POST("check_booking")
    Call<ResponseCheckBooking> requestCheck(
            @Field("id_booking") String id_booking
    );


    @GET("get_request_booking")
    Call<ResponseGetBooking> requestrequest();

    @FormUrlEncoded
    @POST("get_handle_booking")
    Call<ResponseGetBooking> requesthandle(
            @Field("f_idUser") String idUser
    );
}
