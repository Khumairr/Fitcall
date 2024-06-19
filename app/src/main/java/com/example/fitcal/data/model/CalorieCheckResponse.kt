package com.example.fitcal.data.model

import com.google.gson.annotations.SerializedName

data class CalorieCheckResponse(
    @SerializedName("hasil prediksi adalah") val prediction: String,
    @SerializedName("kalori") val calories: Int,
    @SerializedName("rekomendasi") val recommendation: String
)
