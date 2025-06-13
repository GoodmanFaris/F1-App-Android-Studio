package com.example.f1app.ui.state

import com.example.f1app.data.model.Race

sealed class RaceListUiState {
    object Loading : RaceListUiState()
    data class Success(val races: List<Race>) : RaceListUiState()
    data class Error(val message: String) : RaceListUiState()
}
