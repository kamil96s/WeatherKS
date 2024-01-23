package com.example.weatherks

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherks.details.DetailsActivity
import com.example.weatherks.repository.template.Weather
import com.example.weatherks.ui.theme.WeatherKSTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getWeatherData()

        setContent {
            WeatherKSTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }

    @Composable
    fun MainContent() {
        val weatherState = viewModel.weatherData.observeAsState()
        val weathers = weatherState.value ?: emptyList()

        Showcase(weathers = weathers, onClick = { weather ->
            navigateToDetailsActivity(weather)
        })
    }

    fun navigateToDetailsActivity(weather: Weather) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra("WEATHER_ID", weather.id)
            putExtra("CITY_NAME", weather.name)
            putExtra("TEMP_MIN", weather.main.temp_min)
            putExtra("TEMP_MAX", weather.main.temp_max)
            putExtra("SPEED", weather.wind.speed)
            putExtra("GROUND_LEVEL", weather.main.grnd_level)
            putExtra("PRECIPITATION", weather.main.precipitation)
            putExtra("FEELS_LIKE", weather.main.feels_like)
            putExtra("LAST_UPDATE", weather.main.dt)
            // Dodajemy tutaj dodatkowe informacje, jeśli są potrzebne
        }
        startActivity(intent)
    }

    @Composable
    fun Showcase(weathers: List<Weather>, onClick: (Weather) -> Unit) {
        if (weathers.isEmpty()) {
            ShowLoadingIndicator()
        } else {
            LazyColumn {
                items(weathers) { weather ->
                    WeatherCard(weather = weather, onClick = onClick)
                }
            }
        }
    }

    @Composable
    fun WeatherCard(weather: Weather, onClick: (Weather) -> Unit) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(Color.White)
                .clickable { onClick(weather) }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = getSpecificImageResource(weather)),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "${weather.name}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Temperature: ${weather.main.temp}°C")
                    Text(text = "Feels like: ${weather.main.feels_like}°C")
                    Text(text = "Pressure: ${weather.main.pressure}hPa")
                    Text(text = "Humidity: ${weather.main.humidity}%")
                    // Możemy dodać więcej informacji o pogodzie tutaj
                }
            }
        }
    }

    @Composable
    fun ShowLoadingIndicator() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    fun getSpecificImageResource(weather: Weather): Int {
        // Implementacja zwracająca odpowiednią grafikę w zależności od temperatury
        return when {
            weather.main.temp > 20 -> R.drawable.sunny
            weather.main.temp in 15.0..20.0 -> R.drawable.cloudy
            weather.main.temp in 9.0..15.0 -> R.drawable.cloud
            weather.main.temp in 0.0..9.0 -> R.drawable.rainy
            else -> R.drawable.snowy
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        WeatherKSTheme {
            MainContent()
        }
    }
}