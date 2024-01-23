package com.example.weatherks.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.weatherks.R
import com.example.weatherks.repository.WeatherRepository
import com.example.weatherks.repository.template.WeatherDetailsResponse

class DetailsActivity : ComponentActivity() {
    private val detailsViewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(WeatherRepository())
    }

    @SuppressLint("StringFormatMatches", "MissingInflatedId", "StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Odbieramy dane przekazane z MainActivity
        val weatherId = intent.getIntExtra("WEATHER_ID", -1)
        val cityName = intent.getStringExtra("CITY_NAME")
        val temp_min = intent.getDoubleExtra("TEMP_MIN", 0.0)
        val temp_max = intent.getDoubleExtra("TEMP_MAX", 0.0)
        val speed = intent.getDoubleExtra("SPEED", 0.0)
        val grnd_level = intent.getIntExtra("GRND_LEVEL", 0)
        val visibility = intent.getLongExtra("VISIBILITY", 0)
        val precipitation = intent.getDoubleExtra("PRECIPITATION", 0.0)
        val lastUpdate = intent.getStringExtra("LAST_UPDATE")

        // Teraz możemy użyć tych danych do aktualizacji UI
        if (weatherId != -1) {
            // Wyświetlamy szczegółowe informacje
            setContentView(R.layout.activity_details) // Ustawiamy layout z TextView dla ciśnienia itp.
            findViewById<TextView>(R.id.cityNameTextView).text = getString(R.string.city_name_format, cityName)
            findViewById<TextView>(R.id.temp_minTextView).text = getString(R.string.temp_min_format, temp_min)
            findViewById<TextView>(R.id.temp_maxTextView).text = getString(R.string.temp_max_format, temp_max)
            findViewById<TextView>(R.id.speedTextView).text = getString(R.string.speed_format, speed)
            findViewById<TextView>(R.id.grnd_levelTextView).text = getString(R.string.grnd_level_format, grnd_level)
            findViewById<TextView>(R.id.visibilityTextView).text = getString(R.string.visibility_format, visibility)
            findViewById<TextView>(R.id.precipitationTextView).text = getString(R.string.precipitation_format, precipitation)
            findViewById<TextView>(R.id.lastUpdateTextView).text = getString(R.string.last_update_format, lastUpdate)

        } else {
            Toast.makeText(this, "Błąd: nieprawidłowe ID pogody", Toast.LENGTH_LONG).show()
            finish() // Zakończenie działania tej aktywności, jeśli ID jest nieprawidłowe
        }

        // Obserwujemy LiveData z ViewModel, aby zaktualizować UI
        detailsViewModel.weatherDetails.observe(this) { uiState ->
            when {
                uiState.isLoading -> {
                    // Pokaż wskaźnik ładowania
                }
                uiState.error != null -> {
                    // Pokaż komunikat o błędzie
                    Toast.makeText(this, "Błąd: ${uiState.error}", Toast.LENGTH_LONG).show()
                }
                uiState.data != null -> {
                    // Wyświetlamy szczegółowe dane pogodowe
                    displayWeatherDetails(uiState.data)
                }
            }
        }
    }

    private fun displayWeatherDetails(details: WeatherDetailsResponse) {
        // Aktualizacja interfejsu użytkownika na podstawie pobranych danych
        // Możemy tutaj zaktualizować TextView lub inne elementy UI z detalami pogody
    }
}