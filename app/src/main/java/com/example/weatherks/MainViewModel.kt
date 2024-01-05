package com.example.weatherks

import android.util.Log
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

    private val mutableWeatherData = MutableLiveData<UserInterface<List<Weather>>>()
    val immutableWeatherData: LiveData<UserInterface<List<Weather>>> = mutableWeatherData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = weatherRepository.getWeatherResponse()
                val weathers = response.body()?.list
                mutableWeatherData.postValue(UserInterface(data = weathers, isLoading = false, error = null))

            } catch (e: Exception) {
                mutableWeatherData.postValue(UserInterface(data = null, isLoading = false, error = e.message))
                Log.e("MainViewModel", "Error occurred", e)
            }
        }
    }
}