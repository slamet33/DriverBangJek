package com.iu33.driverbangjek.ConfigRetrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hp on 12/25/2017.
 */

public class InstanceRetrofit {
    //TODO Konfigurasi Retrofit


    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.95.39/ojeg_server/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Retrofit retrofitGoogle = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/directions/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static ApiService api = retrofit.create(ApiService.class);
    public static ApiService apiGoogle = retrofitGoogle.create(ApiService.class);
}
