package com.example.teleweather.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                /* usé https porque es más seguro e igual la api lo soporta */
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
