package com.example.f1app.data.model


import com.google.gson.annotations.SerializedName

data class DriverResponse(
    @SerializedName("driver_number")
    val driverNumber: Int,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("team_name")
    val teamName: String?,
    @SerializedName("headshot_url")
    val headshotUrl: String?
)