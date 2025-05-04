package com.example.weather.ui.forecast

    data class HourlyForecast(
        val time: String,
        val temperature: String,
        val condition: String
    )