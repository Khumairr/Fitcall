package com.example.fitcal.ui.menu

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.fitcal.R
import com.example.fitcal.ui.bmi.BmiActivity
import com.example.fitcal.ui.burn.BurnActivity
import com.example.fitcal.ui.checkcalori.CheckCalorieActivity
import com.example.fitcal.ui.welcome.WelcomeActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog
import com.example.fitcal.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bmiCalculatorMenuItem = findViewById<ConstraintLayout>(R.id.menu_item_bmi_calculator)
        bmiCalculatorMenuItem.setOnClickListener {
            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }

        val checkCalorieMenuItem = findViewById<ConstraintLayout>(R.id.menu_item_check_calorie)
        checkCalorieMenuItem.setOnClickListener {
            val intent = Intent(this, CheckCalorieActivity::class.java)
            startActivity(intent)
        }

        val burnMenuItem = findViewById<ConstraintLayout>(R.id.menu_item_burn)
        burnMenuItem.setOnClickListener {
            val intent = Intent(this, BurnActivity::class.java)
            startActivity(intent)
        }

        val fabLogout = findViewById<FloatingActionButton>(R.id.fab_logout)
        fabLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah Anda yakin untuk keluar?")
            .setPositiveButton("Ya") { dialog, id ->
                performLogout()
            }
            .setNegativeButton("Tidak") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun performLogout() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Logging out...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        RetrofitClient.instance.logout().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressDialog.dismiss()
                if (response.isSuccessful) {
                    val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("is_logged_in", false)
                    editor.apply()

                    // Arahkan ke aktivitas selamat datang
                    val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Logout gagal. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Terjadi kesalahan. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}