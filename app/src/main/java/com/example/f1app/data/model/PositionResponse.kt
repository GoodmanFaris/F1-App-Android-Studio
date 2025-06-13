package com.example.f1app.data.model



import com.google.gson.annotations.SerializedName

data class PositionResponse(
    @SerializedName("meeting_key")
    val meetingKey: Int,
    @SerializedName("driver_number")
    val driverNumber: Int,
    @SerializedName("position")
    val position: Int,
    @SerializedName("date")
    val date: String
)
