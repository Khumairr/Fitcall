package com.example.fitcal.ui.burn

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitcal.R

class BurnResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_burn_result)

        val userId = intent.getStringExtra("User_ID")
        val kaloriTerbakar = intent.getDoubleExtra("kalori_terbakar", 0.0)

        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val userIdTextView: TextView = findViewById(R.id.userIdTextView)

        resultTextView.text = "Kalori yang dibakar: %.2f".format(kaloriTerbakar)
        userIdTextView.text = "Nama: $userId"
    }
}