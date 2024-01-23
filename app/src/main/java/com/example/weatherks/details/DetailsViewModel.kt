package com.example.weatherks.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherks.UserInterface
import com.example.weatherks.repository.WeatherRepository
import com.example.weatherks.repository.template.WeatherDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    private val _weatherDetails = MutableLiveData<UserInterface<WeatherDetailsResponse>>()
    val weatherDetails: LiveData<UserInterface<WeatherDetailsResponse>> = _weatherDetails

    fun loadWeatherDetails(weatherId: Int) {
        viewModelScope.launch(Dispatchers.IO) { // Wykorzystujemy zakres ViewModelu do operacji asynchronicznej
            _weatherDetails.postValue(UserInterface(isLoading = true))
            try {
                val response = weatherRepository.getWeathersDetails(weatherId.toString())
                if (response.isSuccessful && response.body() != null) {
                    _weatherDetails.postValue(UserInterface(data = response.body()))
                } else {
                    _weatherDetails.postValue(UserInterface(error = "Nie udało się załadować danych."))
                }
            } catch (e: Exception) {
                _weatherDetails.postValue(UserInterface(error = e.message))
            }
        }
    }
}

class DetailsViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Nieznany ViewModel class")
    }
}