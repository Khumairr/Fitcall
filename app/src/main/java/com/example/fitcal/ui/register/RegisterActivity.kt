package com.example.fitcal.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.fitcal.data.model.RegisterRequest
import com.example.fitcal.data.model.RegisterResponse
import com.example.fitcal.data.remote.RetrofitClient
import com.example.fitcal.databinding.ActivityRegisterBinding
import com.example.fitcal.ui.login.LoginActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.HttpException

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.loadingProgressBar.visibility = View.VISIBLE
                lifecycleScope.launch {
                    try {
                        val call = RetrofitClient.instance.register(RegisterRequest(email, password))
                        call.enqueue(object : Callback<RegisterResponse> {
                            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                                binding.loadingProgressBar.visibility = View.GONE
                                if (response.isSuccessful) {
                                    Toast.makeText(this@RegisterActivity, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this@RegisterActivity, "Registrasi gagal!", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                                binding.loadingProgressBar.visibility = View.GONE
                                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    } catch (e: HttpException) {
                        binding.loadingProgressBar.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Email dan kata sandi harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}