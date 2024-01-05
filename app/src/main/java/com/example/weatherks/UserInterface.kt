package com.example.weatherks

data class UserInterface<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
