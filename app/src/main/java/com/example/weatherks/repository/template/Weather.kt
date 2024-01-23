package com.example.weatherks.repository.template

data class Weather(
    val id: Int,
    val name: String,
    val main: WeatherInfo,
    val wind: Wind,
    val sys: Sys
)

data class WeatherInfo(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val seaLevel: Int,
    val grnd_level: Int,
    val visibility: Long,
    val precipitation: Double,
    val rain: Double,
    val dt: String //lastupdate
)

data class Wind(
    val speed: Double,
    val direction: Int
)

data class Sys(
    val sunrise: Long
)