package com.example.weatherks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherks.repository.WeatherRepository
import com.example.weatherks.repository.template.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val weatherRepository = WeatherRepository()

    private val _weatherData = MutableLiveData<List<Weather>>()
    val weatherData: LiveData<List<Weather>> = _weatherData

    fun getWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = weatherRepository.getWeatherResponse()
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        _weatherData.postValue(responseBody.list)
                    }
                } else {
                    // Obsłuż błąd
                }
            } catch (e: Exception) {
                // Obsłuż wyjątek
            }
        }
    }
}