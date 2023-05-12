package com.example.todo.Retrofit.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Call<WeatherResponse> getWeatherByCityName(@Query("lat") Double lat, @Query("lon") Double lon);

    @GET("weather")
    Call<WeatherResponse> getWeatherByCityName(@Query("lat") Double lat, @Query("lon") Double lon, @Query("exclude") List<String> excludes);

}
