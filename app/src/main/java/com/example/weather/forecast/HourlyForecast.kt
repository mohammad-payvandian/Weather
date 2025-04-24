package com.example.weather.forecast

    data class HourlyForecast(
        val time: String,
        val temperature: String,
        val condition: String
    )