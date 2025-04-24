package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.forecast.ForecastAdapter
import com.example.weather.forecast.HourlyForecast

class ForecastWeatherFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var forecastList: List<HourlyForecast>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast_weather, container, false)

        // پیدا کردن ویو‌ها
        recyclerView = view.findViewById(R.id.recyclerViewForecast)

        // شبیه‌سازی داده‌های آب‌وهوا
        forecastList = listOf(
            HourlyForecast("18:00", "27°C", "Sunny"),
            HourlyForecast("19:00", "27°C", "Sunny"),
            HourlyForecast("20:00", "26°C", "Sunny"),
            HourlyForecast("21:00", "22°C", "Sunny"),
            HourlyForecast("22:00", "20°C", "Clear"),
            HourlyForecast("23:00", "19°C", "Clear"),
            HourlyForecast("00:00", "19°C", "Clear"),
            HourlyForecast("01:00", "18°C", "Slightly cloudy"),
            HourlyForecast("02:00", "17°C", "Slightly cloudy"),
            HourlyForecast("03:00", "17°C", "Slightly cloudy"),
            HourlyForecast("04:00", "16°C", "Slightly cloudy")
        )

        // تنظیم RecyclerView
        forecastAdapter = ForecastAdapter(forecastList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = forecastAdapter

        return view
    }
}
