package com.example.weatherks.repository.template

data class WeatherResponse(
    val cnt: Int,
    val list: List<Weather>
)