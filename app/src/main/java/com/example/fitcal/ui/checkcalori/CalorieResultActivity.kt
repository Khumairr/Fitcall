package com.example.fitcal.ui.checkcalori

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
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
        val resultImageView: ImageView = findViewById(R.id.result_image)

        val prediction = intent.getStringExtra("prediction") ?: "Unknown"
        val calories = intent.getIntExtra("calories", 0)
        val recommendation = intent.getStringExtra("recommendation") ?: "No recommendation"

        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        if (imageUri != null) {
            resultImageView.setImageURI(imageUri)
        }

        predictionTextView.text = prediction
        caloriesTextView.text = "$calories kcal"
        recommendationTextView.text = recommendation
    }

}