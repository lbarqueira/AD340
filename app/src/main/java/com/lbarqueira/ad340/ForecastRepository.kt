package com.lbarqueira.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random


// Create a Repository
// Doing 2 things: 1 - Loading data for us, and 2 - providing that data out to our activity
// LiveData is a data holder class that can be observed within a given lifecycle.

class ForecastRepository {
    // 2 - how our activity get data from our repository
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>() // private property

    // Public LiveData - so the activity access it and only the repository can modify the data
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    // 1 - Load the data into repository
    fun loadForecast(zipcode: String) {
        // random temperature values
        val randomValues = List(7) {
            Random.nextFloat().rem(100) * 100
        }
        val forecastItems = randomValues.map { temp ->
            DailyForecast(temp, "Partly Cloudy")
        }

        _weeklyForecast.value = forecastItems

    }
}