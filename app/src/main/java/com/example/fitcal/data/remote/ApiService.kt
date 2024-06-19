package com.example.fitcal.data.remote

import com.example.fitcal.data.model.LoginRequest
import com.example.fitcal.data.model.LoginResponse
import com.example.fitcal.data.model.RegisterRequest
import com.example.fitcal.data.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}