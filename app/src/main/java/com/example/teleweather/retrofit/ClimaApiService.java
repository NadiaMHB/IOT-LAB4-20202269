package com.example.teleweather.retrofit;

import com.example.teleweather.model.Location;
import com.example.teleweather.model.PronosticoResponse;
import com.example.teleweather.model.SportsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClimaApiService {

    @GET("search.json")
    Call<List<Location>> buscarLocacion(
            @Query("key") String apiKey,
            @Query("q") String query);

    @GET("forecast.json")
    Call<PronosticoResponse> obtenerPronostico(
            @Query("key") String apiKey,
            @Query("q") String coordenadas,
            @Query("days") int dias
    );

    @GET("sports.json")
    Call<SportsResponse> obtenerEventosDeportivos(
            @Query("key") String apiKey,
            @Query("q") String location
    );

}
