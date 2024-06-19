package com.example.fitcal.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.fitcal.data.model.LoginRequest
import com.example.fitcal.data.remote.RetrofitClient
import com.example.fitcal.databinding.ActivityLoginBinding
import com.example.fitcal.ui.menu.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.loadingProgressBar.visibility = View.VISIBLE
                lifecycleScope.launch {
                    try {
                        val response = withContext(Dispatchers.IO) {
                            RetrofitClient.instance.login(LoginRequest(email, password)).execute()
                        }
                        binding.loadingProgressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            // Save login status to SharedPreferences
                            with(sharedPreferences.edit()) {
                                putBoolean("is_logged_in", true)
                                apply()
                            }
                            Toast.makeText(this@LoginActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Login gagal!", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: HttpException) {
                        binding.loadingProgressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

}