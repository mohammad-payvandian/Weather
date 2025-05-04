package com.example.weather.data

data class WeatherResponse(
    val count: Int,
    val data: List<WeatherData>
)

data class WeatherData(
    val app_temp: Double,
    val aqi: Int,
    val city_name: String,
    val clouds: Int,
    val country_code: String,
    val datetime: String,
    val dewpt: Double,
    val dhi: Int,
    val dni: Int,
    val elev_angle: Double,
    val ghi: Int,
    val gust: Double,
    val h_angle: Double,
    val lat: Double,
    val lon: Double,
    val ob_time: String,
    val pod: String,
    val precip: Int,
    val pres: Int,
    val rh: Int,
    val slp: Int,
    val snow: Int,
    val solar_rad: Int,
    val sources: List<String>,
    val state_code: String,
    val station: String,
    val sunrise: String,
    val sunset: String,
    val temp: Double,
    val timezone: String,
    val ts: Long,
    val uv: Int,
    val vis: Int,
    val weather: WeatherDescription,
    val wind_cdir: String,
    val wind_cdir_full: String,
    val wind_dir: Int,
    val wind_spd: Double
)

data class WeatherDescription(
    val icon: String,
    val description: String,
    val code: Int
)
