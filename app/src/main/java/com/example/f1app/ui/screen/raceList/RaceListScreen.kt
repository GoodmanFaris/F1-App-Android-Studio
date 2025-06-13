package com.example.f1app.ui.screen.raceList

import RacesViewModel
import android.graphics.ImageFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.f1app.data.model.Race
import com.example.f1app.ui.component.F1TopBar
import com.example.f1app.ui.component.SeasonSelector
import com.example.f1app.ui.state.RaceListUiState
import com.example.f1app.R
import com.example.f1app.ui.component.DurationButton
import com.example.f1app.ui.component.GradientImage
import com.example.f1app.ui.component.RaceItem
import com.example.f1app.ui.component.getTrackImageRes


@Composable
fun RaceListScreen(
    navController: NavHostController,
    viewModel: RacesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedSeason by viewModel.selectedSeason
    val searchQuery = remember { mutableStateOf("") }
    val selectedDurationMinutes = remember { mutableStateOf(1) }

    Scaffold(
        topBar = { F1TopBar(modifier = Modifier, navController = navController) },
        containerColor = Color.Black
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Row{
                Image(painter = painterResource(id = R.drawable.logo_red),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 10.dp))

                Text(
                    text = "Season $selectedSeason",
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 15.dp, top = 20.dp),
                    fontFamily = FontFamily.SansSerif
                )
            }


            HorizontalDivider(
                color = Color.Red,
                thickness = 6.dp,
                modifier = Modifier.padding(start = 35.dp)
            )

            //Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                label = { Text(stringResource(R.string.search_races), color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Red,
                    unfocusedTextColor = Color.Red,
                    focusedIndicatorColor = Color.Red,
                    unfocusedIndicatorColor = Color.White,
                    cursorColor = Color.Red
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = stringResource(R.string.race_playback_speed),
                color = Color.Black,
                modifier = Modifier.padding(start = 15.dp),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif
                )

            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DurationButton(text = stringResource(R.string.slow), isSelected = selectedDurationMinutes.value == 1) {
                    selectedDurationMinutes.value = 1
                }
                DurationButton(text = stringResource(R.string.mid), isSelected = selectedDurationMinutes.value == 2) {
                    selectedDurationMinutes.value = 2
                }
                DurationButton(text = stringResource(R.string.fast), isSelected = selectedDurationMinutes.value == 3) {
                    selectedDurationMinutes.value = 3
                }
            }


            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(
                color = Color.Black,
                thickness = 6.dp,
                modifier = Modifier.padding(end = 35.dp)
            )

            when (uiState) {
                is RaceListUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.loading), color = Color.Gray)
                    }
                }

                is RaceListUiState.Success -> {
                    val races = (uiState as RaceListUiState.Success).races
                    val filteredRaces = races.filter { race ->
                        race.raceName.contains(searchQuery.value, ignoreCase = true)
                    }

                    LazyColumn {
                        items(filteredRaces) { race ->
                            RaceItem(race) {
                                navController.navigate("race_countdown/${race.meetingKey}/${race.sessionKey}/${selectedDurationMinutes.value}")
                            }
                        }
                    }
                }

                is RaceListUiState.Error -> {
                    val message = (uiState as RaceListUiState.Error).message
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(message, color = Color.Red)
                    }
                }
            }
        }
    }
}



