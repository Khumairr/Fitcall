package com.example.fitcal.ui.burn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcal.R
import com.example.fitcal.data.model.BurnCalorieRequest
import com.example.fitcal.data.model.BurnCalorieResponse
import com.example.fitcal.data.remote.RetrofitClient
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BurnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_burn)

        val userIdEditText: EditText = findViewById(R.id.userIdEditText)
        val genderEditText: EditText = findViewById(R.id.genderEditText)
        val ageEditText: EditText = findViewById(R.id.ageEditText)
        val heightEditText: EditText = findViewById(R.id.heightEditText)
        val weightEditText: EditText = findViewById(R.id.weightEditText)
        val durationEditText: EditText = findViewById(R.id.durationEditText)
        val heartRateEditText: EditText = findViewById(R.id.heartRateEditText)
        val bodyTempEditText: EditText = findViewById(R.id.bodyTempEditText)
        val burnButton: Button = findViewById(R.id.burnButton)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        burnButton.setOnClickListener {
            val userId = userIdEditText.text.toString()
            val gender = genderEditText.text.toString()
            val age = ageEditText.text.toString().toIntOrNull()
            val height = heightEditText.text.toString().toIntOrNull()
            val weight = weightEditText.text.toString().toIntOrNull()
            val duration = durationEditText.text.toString().toIntOrNull()
            val heartRate = heartRateEditText.text.toString().toIntOrNull()
            val bodyTemp = bodyTempEditText.text.toString().toDoubleOrNull()

            if (userId.isNotBlank() && gender.isNotBlank() && age != null && height != null && weight != null && duration != null && heartRate != null && bodyTemp != null) {
                val request = BurnCalorieRequest(userId, gender, age, height, weight, duration, heartRate, bodyTemp)
                Log.d("BurnActivity", "Request data: $request")

                progressBar.visibility = View.VISIBLE
                burnButton.isEnabled = false

                RetrofitClient.instance.burnCalories(request).enqueue(object : Callback<BurnCalorieResponse> {
                    override fun onResponse(call: Call<BurnCalorieResponse>, response: Response<BurnCalorieResponse>) {
                        progressBar.visibility = View.GONE
                        burnButton.isEnabled = true
                        if (response.isSuccessful) {
                            val result = response.body()
                            Log.d("BurnActivity", "Response: $result")

                            val intent = Intent(this@BurnActivity, BurnResultActivity::class.java).apply {
                                putExtra("User_ID", result?.User_ID)
                                putExtra("kalori_terbakar", result?.kalori_terbakar?.firstOrNull() ?: 0.0)
                            }
                            startActivity(intent)
                        } else {
                            Log.e("BurnActivity", "Error response code: ${response.code()}")
                            Log.e("BurnActivity", "Error response body: ${response.errorBody()?.string()}")
                            Toast.makeText(this@BurnActivity, "Gagal mendapatkan respon dari server", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BurnCalorieResponse>, t: Throwable) {
                        progressBar.visibility = View.GONE
                        burnButton.isEnabled = true
                        Log.e("BurnActivity", "Error: ${t.message}")
                        Toast.makeText(this@BurnActivity, "Gagal terhubung ke server: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Mohon isi semua field dengan benar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}