package com.example.weatherks.repository

import com.example.weatherks.repository.template.WeatherDetailsResponse
import com.example.weatherks.repository.template.WeatherResponse
import retrofit2.Response

class WeatherRepository {

    suspend fun getWeatherResponse(): Response<WeatherResponse> =
        WeatherService.weatherService.getWeatherResponse()
    suspend fun getWeathersDetails(id: String): Response<WeatherDetailsResponse> {
        return WeatherService.weatherService.getWeathersDetails(id)
    }
}