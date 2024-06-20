package com.example.fitcal.data.model

data class BurnCalorieRequest(
    val User_ID: String,
    val Gender: String,
    val Age: Int,
    val Height: Int,
    val Weight: Int,
    val Duration: Int,
    val Heart_Rate: Int,
    val Body_Temp: Double
)

