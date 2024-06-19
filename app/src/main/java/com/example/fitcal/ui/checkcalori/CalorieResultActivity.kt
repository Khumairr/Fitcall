package com.example.fitcal.ui.checkcalori

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcal.R

class CalorieResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie_result)

        val predictionTextView: TextView = findViewById(R.id.prediction)
        val caloriesTextView: TextView = findViewById(R.id.calories)
        val recommendationTextView: TextView = findViewById(R.id.recommendation)

        val prediction = intent.getStringExtra("prediction") ?: "Unknown"
        val calories = intent.getIntExtra("calories", 0)
        val recommendation = intent.getStringExtra("recommendation") ?: "No recommendation"

        predictionTextView.text = prediction
        caloriesTextView.text = "$calories kcal"
        recommendationTextView.text = recommendation
    }

}