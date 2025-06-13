package com.example.f1app.data.model


import com.google.gson.annotations.SerializedName

data class SessionResponse(
    @SerializedName("session_type")
    val sessionType: String?,
    @SerializedName("session_name")
    val sessionName: String?,
    val location: String?,
    @SerializedName("country_name")
    val countryName: String?,
    @SerializedName("date_start")
    val dateStart: String?,
    val year: Int?,
    @SerializedName("session_key")
    val sessionKey: Int,
    @SerializedName("meeting_key")
    val meetingKey: Int
)
