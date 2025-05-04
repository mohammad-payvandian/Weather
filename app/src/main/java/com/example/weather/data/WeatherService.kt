package com.example.weather.data


import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current")
    suspend fun getCurrentWeather(
        @Query("city") city: String,
        @Query("key") apiKey: String = "848827afa58842d1bdc16d0032ceef1b"
    ): WeatherResponse
}