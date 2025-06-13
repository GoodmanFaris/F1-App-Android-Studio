import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.f1app.data.remote.F1Api
import com.example.f1app.data.model.Race
import com.example.f1app.ui.state.RaceListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RacesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RaceListUiState>(RaceListUiState.Loading)
    val uiState: StateFlow<RaceListUiState> = _uiState

    private val _selectedSeason = mutableStateOf(2023)
    val selectedSeason: State<Int> get() = _selectedSeason

    init {
        fetchRaces()
    }

    fun setSelectedSeason(season: Int) {
        _selectedSeason.value = season
        fetchRaces()
    }

    private fun fetchRaces() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = RaceListUiState.Loading
            try {
                val sessions = F1Api.retrofitService.getSessions()

                val racesList = sessions
                    .filter { it.sessionType == "Race" && it.year == _selectedSeason.value }
                    .map {
                        Race(
                            sessionKey = it.sessionKey,
                            meetingKey = it.meetingKey,
                            raceName = "${it.location ?: "Unknown"}- ${it.sessionName ?: "Unknown Session"}",
                            circuitName = it.countryName ?: "Unknown Circuit",
                            trackName = it.location,
                            date = it.dateStart?.split("T")?.get(0) ?: "Unknown Date"
                        )
                    }


                _uiState.value = RaceListUiState.Success(racesList)
            } catch (e: Exception) {
                _uiState.value = RaceListUiState.Error("Greška prilikom učitavanja podataka.")
            }
        }
    }
}

