package com.example.weather.ui.forecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.FragmentForecastWeatherBinding
import com.example.weather.databinding.FragmentMainWeatherBinding
import com.example.weather.ui.WeatherUiState
import com.example.weather.ui.WeatherViewModel
import com.example.weather.ui.forecast.ForecastAdapter
import com.example.weather.ui.forecast.HourlyForecast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ForecastWeatherFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var forecastList: List<HourlyForecast>
    private lateinit var binding: FragmentForecastWeatherBinding
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForecastWeatherBinding.inflate(inflater, container, false)
        val toolbar = binding.myToolbar
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_24) // آیکون فلش به عقب
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // پیدا کردن ویو‌ها
        recyclerView = binding.recyclerViewForecast

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

        //  RecyclerView
        forecastAdapter = ForecastAdapter(forecastList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = forecastAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.weatherState
            .onEach { state ->
                when (state) {
                    is WeatherUiState.Success -> {
                        val data = state.weatherData
                        // نمایش اطلاعات در UI:
                        binding.tvCondition.text = data.weather.description
                        binding.tvTemperature.text = "${data.temp}°C"
                        binding.tvTime.text = data.ts.toString()
                        // یا: forecastAdapter.submitList(data.hourlyForecast)
                    }
                    is WeatherUiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    is WeatherUiState.Loading -> {
                        // نشان دادن ProgressBar در صورت نیاز
                    }
                    is WeatherUiState.Empty -> {
                        // حالت اولیه
                    }

                    else -> {}
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

}
