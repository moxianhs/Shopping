package com.example.app.shopping.weather;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {

    String BASE_URL = "http://t.weather.sojson.com/api/weather/city/";


    @GET("{city}")
    Call<HashMap<String, Object>> getWeather(@Path("city") String city);
}
