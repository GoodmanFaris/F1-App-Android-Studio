package com.example.f1app.viewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app.data.model.DriverResponse
import com.example.f1app.data.model.PositionResponse
import com.example.f1app.data.remote.F1Api
import com.example.f1app.ui.state.RaceUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.Duration

class RacePositionViewModel : ViewModel() {

    private val _positions = MutableStateFlow<List<PositionResponse>>(emptyList())
    val positions: StateFlow<List<PositionResponse>> get() = _positions

    private val _drivers = MutableStateFlow<List<DriverResponse>>(emptyList())

    private val _currentFrame = MutableStateFlow<List<PositionResponse>>(emptyList())

    private val _raceTimeMillis = MutableStateFlow(0L)
    val raceTimeMillis: StateFlow<Long> get() = _raceTimeMillis

    private val _raceFinished = MutableStateFlow(false)
    val raceFinished: StateFlow<Boolean> get() = _raceFinished

    private val _uiState = MutableStateFlow<RaceUiState>(RaceUiState.Loading)
    val uiState: StateFlow<RaceUiState> = _uiState

    val currentFrame: StateFlow<List<PositionResponse>> = _currentFrame
    val drivers: StateFlow<List<DriverResponse>> = _drivers




    private var startTime: Instant? = null

    private fun cleanDate(date: String): String {
        val fixedZone = date.replace("+00:00", "Z")
        return fixedZone.replace(Regex("(\\.\\d{3})\\d*"), "$1")
    }


    fun loadPositions(meetingKey: Int, sessionKey: Int, durationMinutes: Int) {
        viewModelScope.launch {
            _uiState.value = RaceUiState.Loading
            try {
                val data = F1Api.retrofitService.getPositions(meetingKey, sessionKey)
                    .sortedBy { Instant.parse(cleanDate(it.date)) }

                val driverData = F1Api.retrofitService.getDrivers(meetingKey)
                _drivers.value = driverData

                if (data.isNotEmpty()) {
                    _positions.value = data
                    startRaceSimulation(durationMinutes)
                    _uiState.value = RaceUiState.Success
                } else {
                    _uiState.value = RaceUiState.Error("No data available.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = RaceUiState.Error("Failed to load race data.")
            }
        }
    }


    fun startRaceSimulation(durationMinutes: Int) {
        viewModelScope.launch {
            if (_positions.value.isEmpty()) return@launch

            startTime = Instant.parse(cleanDate(_positions.value.first().date))
            var currentTime = startTime!!

            while (true) {
                val elapsed = Duration.between(startTime, currentTime).toMillis()

                val frameData = _positions.value.filter {
                    val eventTime = Instant.parse(cleanDate(it.date))
                    Duration.between(startTime, eventTime).toMillis() <= elapsed
                }
                    .groupBy { it.driverNumber }
                    .map { (_, events) -> events.last() }

                _currentFrame.value = frameData.sortedBy { it.position }

                _raceTimeMillis.value = elapsed

                delay(200)
                currentTime = currentTime.plusMillis((durationMinutes * 10000).toLong())

                if (frameData.isEmpty() || currentTime.isAfter(Instant.parse(cleanDate(_positions.value.last().date)))) {
                    _raceFinished.value = true
                    break
                }
            }
        }
    }


}
