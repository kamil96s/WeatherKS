package com.example.weatherks.repository

import com.example.weatherks.repository.template.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WeatherService {

    @GET("/data/2.5/group?id=3675707,323777,5128581,292223,3067696,756135,2643123,2729907&units=metric&appid=5ca6c85333f880281b794d3710930206")
    suspend fun getWeatherResponse(): Response<WeatherResponse>

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org"
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val weatherService: WeatherService by lazy {
            retrofit.create(WeatherService::class.java)
        }
    }

}