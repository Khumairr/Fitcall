package com.example.fitcal.ui.bmi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcal.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val bmi = intent.getDoubleExtra("BMI_RESULT", 0.0)
        val bmiCategory = intent.getStringExtra("BMI_CATEGORY")
        val advice = intent.getStringExtra("ADVICE")

        val bmiResultTextView: TextView = findViewById(R.id.bmiResultTextView)
        val bmiCategoryTextView: TextView = findViewById(R.id.bmiCategoryTextView)
        val adviceTextView: TextView = findViewById(R.id.adviceTextView)

        bmiResultTextView.text = getString(R.string.bmi_result, bmi)
        bmiCategoryTextView.text = bmiCategory
        adviceTextView.text = advice
    }
}