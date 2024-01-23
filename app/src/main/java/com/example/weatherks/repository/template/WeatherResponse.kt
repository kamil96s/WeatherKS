package com.example.weatherks.repository.template

data class WeatherResponse(
    val cnt: Int,
    val list: List<Weather>
)

data class WeatherDetailsResponse(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
    // inne pola specyficzne dla szczegółów pogodowych
)