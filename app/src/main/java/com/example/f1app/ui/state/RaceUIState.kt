package com.example.f1app.ui.state

import com.example.f1app.data.model.DriverResponse
import com.example.f1app.data.model.PositionResponse

sealed class RaceUiState {
    object Loading : RaceUiState()
    object Success : RaceUiState()
    data class Error(val message: String) : RaceUiState()
}
