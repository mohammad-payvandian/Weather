package com.example.weather.data

import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: WeatherService
) {
    suspend fun getCurrentWeather(city: String): WeatherResponse {
        return apiService.getCurrentWeather(city)
    }
}

