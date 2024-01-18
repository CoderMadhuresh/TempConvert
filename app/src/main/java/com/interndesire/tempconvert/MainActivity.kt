package com.interndesire.tempconvert

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var unitSpinner: Spinner
    private lateinit var temperatureEditText: EditText
    private lateinit var fahrenheitTextView: TextView
    private lateinit var kelvinTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        unitSpinner = findViewById(R.id.unitSpinner)
        temperatureEditText = findViewById(R.id.temperatureEditText)
        fahrenheitTextView = findViewById(R.id.fahrenheitTextView)
        kelvinTextView = findViewById(R.id.kelvinTextView)

        temperatureEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertTemperature()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        unitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertTemperature()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun convertTemperature() {
        val temperatureText = temperatureEditText.text.toString()

        if (temperatureText.isNotEmpty()) {
            val temperature: Double = try {
                temperatureText.toDouble()
            } catch (e: NumberFormatException) {
                // Handle the case where the input is not a valid double
                // You can show an error message or set temperature to 0, depending on your requirements
                0.0
            }

            when (unitSpinner.selectedItem.toString()) {
                "Celsius" -> {
                    fahrenheitTextView.text = getString(R.string.fahrenheit_output, "%.3f".format(celsiusToFahrenheit(temperature)))
                    kelvinTextView.text = getString(R.string.kelvin_output, "%.3f".format(celsiusToKelvin(temperature)))
                }
                "Fahrenheit" -> {
                    val celsiusValue = fahrenheitToCelsius(temperature)
                    fahrenheitTextView.text = getString(R.string.celsius_output, "%.3f".format(celsiusValue))
                    kelvinTextView.text = getString(R.string.kelvin_output, "%.3f".format(celsiusToKelvin(celsiusValue)))
                }
                "Kelvin" -> {
                    val celsiusValue = kelvinToCelsius(temperature)
                    fahrenheitTextView.text = getString(R.string.celsius_output, "%.3f".format(celsiusValue))
                    kelvinTextView.text = getString(R.string.fahrenheit_output, "%.3f".format(celsiusToFahrenheit(celsiusValue)))
                }
            }
        } else {
            fahrenheitTextView.text = getString(R.string.fahrenheit_output, "")
            kelvinTextView.text = getString(R.string.kelvin_output, "")
        }
    }



    private fun celsiusToFahrenheit(celsius: Double): Double {
        return celsius * 9 / 5 + 32
    }

    private fun celsiusToKelvin(celsius: Double): Double {
        return celsius + 273.15
    }

    private fun fahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }

    private fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }
}
