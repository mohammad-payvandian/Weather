package com.example.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.WeatherData
import com.example.weather.data.WeatherRepository
import com.example.weather.data.WeatherService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    fun getCurrentWeather(city: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            try {
                val response = repository.getCurrentWeather(city)
                if (response.data.isNotEmpty()) {
                    _weatherState.value = WeatherUiState.Success(response.data[0])
                } else {
                    _weatherState.value = WeatherUiState.Error("No weather data found")
                }
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error(e.message ?: "An error occurred")
            }
        }
    }
}

sealed class WeatherUiState {
    object Empty : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(val weatherData: WeatherData) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}