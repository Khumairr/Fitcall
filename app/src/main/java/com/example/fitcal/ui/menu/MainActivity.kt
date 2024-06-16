package com.example.fitcal.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.fitcal.R
import com.example.fitcal.ui.bmi.BmiActivity
import com.example.fitcal.ui.checkcalori.CheckCalorieActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bmiCalculatorMenuItem = findViewById<ConstraintLayout>(R.id.menu_item_bmi_calculator)
        bmiCalculatorMenuItem.setOnClickListener {
            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }
    }
}