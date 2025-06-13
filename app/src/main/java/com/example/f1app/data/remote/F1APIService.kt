package com.example.f1app.data.remote


import com.example.f1app.data.model.DriverResponse
import com.example.f1app.data.model.PositionResponse
import com.example.f1app.data.model.SessionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface F1ApiService {

    @GET("v1/sessions")
    suspend fun getSessions(): List<SessionResponse>

    @GET("v1/drivers")
    suspend fun getDrivers(
        @Query("meeting_key") meetingKey: Int
    ): List<DriverResponse>

    @GET("v1/position")
    suspend fun getPositions(
        @Query("meeting_key") meetingKey: Int,
        @Query("session_key") sessionKey: Int
    ): List<PositionResponse>


}
