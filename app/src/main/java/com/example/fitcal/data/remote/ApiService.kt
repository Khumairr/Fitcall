package com.example.fitcal.data.remote

import com.example.fitcal.data.model.CalorieCheckResponse
import com.example.fitcal.data.model.LoginRequest
import com.example.fitcal.data.model.LoginResponse
import com.example.fitcal.data.model.RegisterRequest
import com.example.fitcal.data.model.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @Multipart
    @POST("predict")
    fun checkCalories(@Part image: MultipartBody.Part): Call<CalorieCheckResponse>
}