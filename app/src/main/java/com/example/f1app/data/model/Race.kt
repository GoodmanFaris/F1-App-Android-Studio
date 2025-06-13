package com.example.f1app.data.model


data class Race(
    val sessionKey: Int,
    val meetingKey: Int,
    val raceName: String,
    val circuitName: String,
    val date: String,
    val trackName: String?
)

