package com.lbarqueira.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()

    // region Setup methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        val enterButton: Button = findViewById(R.id.enterButton)

        enterButton.setOnClickListener {
            // Toast.makeText(this,"Button Clicked",Toast.LENGTH_SHORT).show()

            Log.i("MainActivity", "Button Clicked")
            val zipcode: String = zipcodeEditText.text.toString()

            if (zipcode.length != 6) {
                Toast.makeText(this, R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            } else {
                forecastRepository.loadForecast(zipcode)
            }

        }

        // create a reference to our RecycleView, like other Views
        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        // set a layout manager to Recycle View - Controls how individual views are laid out
        // on the screen
        forecastList.layoutManager = LinearLayoutManager(this)


        // create an instance of adapter and connect to recyclerview
        val dailyForecastAdapter = DailyForecastAdapter {
            Toast.makeText(this, "Clicked Item", Toast.LENGTH_SHORT).show()
        }
        forecastList.adapter = dailyForecastAdapter



        // create an observer
        val weeklyForecastObserver = Observer<List<DailyForecast>> { forecastItems ->
            // update our list adapter
            //Toast.makeText(this, "Loaded Items", Toast.LENGTH_SHORT).show()
            dailyForecastAdapter.submitList(forecastItems)

        }

        // add an observer to the repository
        // an activity is a LifecycleOwner and weeklyForecastObserver is an observer
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

    }
}