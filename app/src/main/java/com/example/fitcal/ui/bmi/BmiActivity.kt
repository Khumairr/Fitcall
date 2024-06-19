package com.example.fitcal.ui.bmi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitcal.R

class BmiActivity : AppCompatActivity() {

    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bmi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        weightEditText = findViewById(R.id.weightEditText)
        heightEditText = findViewById(R.id.heightEditText)
        calculateButton = findViewById(R.id.calculateButton)

        calculateButton.setOnClickListener {
            calculateBmi()
        }
    }

    private fun calculateBmi() {
        val weight = weightEditText.text.toString().toDoubleOrNull()
        val height = heightEditText.text.toString().toDoubleOrNull()

        if (weight != null && height != null) {
            val heightInMeters = height / 100
            val bmi = weight / (heightInMeters * heightInMeters)
            val (bmiCategory, advice) = when {
                bmi < 18.5 -> getString(R.string.underweight) to getString(R.string.advice_underweight)
                bmi < 24.9 -> getString(R.string.normal_weight) to getString(R.string.advice_normal)
                bmi < 29.9 -> getString(R.string.overweight) to getString(R.string.advice_overweight)
                else -> getString(R.string.obesity) to getString(R.string.advice_obesity)
            }
            displayBmiResult(bmi, bmiCategory, advice)
        } else {
        }
    }

    private fun displayBmiResult(bmi: Double, bmiCategory: String, advice: String) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("BMI_RESULT", bmi)
            putExtra("BMI_CATEGORY", bmiCategory)
            putExtra("ADVICE", advice)
        }
        startActivity(intent)
    }
}