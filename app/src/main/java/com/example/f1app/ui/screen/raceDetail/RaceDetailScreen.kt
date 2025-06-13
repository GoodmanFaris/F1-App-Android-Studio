package com.example.f1app.ui.screen.raceDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.f1app.R
import com.example.f1app.data.model.DriverResponse
import com.example.f1app.data.model.PositionResponse
import com.example.f1app.ui.component.DriverPositionRow
import com.example.f1app.ui.component.F1TopBarNoSS
import com.example.f1app.ui.state.RaceUiState
import com.example.f1app.viewModel.RacePositionViewModel
import java.time.LocalDate


@Composable
fun RaceDetailScreen(
    navController: NavHostController,
    meetingKey: Int,
    sessionKey: Int,
    durationMinutes: Int,
    viewModel: RacePositionViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadPositions(meetingKey, sessionKey, durationMinutes)
    }

    val raceTimeMillis by viewModel.raceTimeMillis.collectAsState()
    val raceFinished by viewModel.raceFinished.collectAsState()

    val uiState by viewModel.uiState.collectAsState()
    val currentFrame by viewModel.currentFrame.collectAsState()
    val drivers by viewModel.drivers.collectAsState()




    Scaffold(
        topBar = {
            F1TopBarNoSS(modifier = Modifier, navController = navController)
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Text(
                text = "Race Live Positions",
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 16.dp)
            )

            when (uiState) {
                is RaceUiState.Loading -> {
                    Text(
                        text = "Loading...",
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is RaceUiState.Success -> {
                    val seconds = (raceTimeMillis / 1000) % 60
                    val minutes = (raceTimeMillis / (1000 * 60)) % 60
                    val hours = (raceTimeMillis / (1000 * 60 * 60))

                    Text(
                        text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    HorizontalDivider(
                        color = Color.Red,
                        thickness = 6.dp,
                        modifier = Modifier.padding(start = 35.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (raceFinished) {
                        Text(
                            text = stringResource(R.string.race_finished),
                            color = Color.Red,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn {
                        items(currentFrame) { driverPosition ->
                            val driverInfo = drivers.find { it.driverNumber == driverPosition.driverNumber }
                            DriverPositionRow(driverPosition, driverInfo)
                        }
                    }
                }

                is RaceUiState.Error -> {
                    val errorMessage = (uiState as RaceUiState.Error).message
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}



