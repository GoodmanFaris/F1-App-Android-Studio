package com.example.f1app.ui.screen.countdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.f1app.R
import com.example.f1app.ui.component.F1TopBar
import kotlinx.coroutines.delay

@Composable

fun RaceCountdownScreen(
    navController: NavHostController,
    meetingKey: Int,
    sessionKey: Int,
    durationMinutes: Int
)  {
    var currentImageIndex by remember { mutableStateOf(0) }
    val images = listOf(
        R.drawable.light1,
        R.drawable.light2,
        R.drawable.light3,
        R.drawable.light4,
        R.drawable.light5
    )

    Column(
        modifier = Modifier.background(color = Color.White).fillMaxSize()
    ){

        LaunchedEffect(Unit) {
            while (currentImageIndex < images.size) {
                delay(1000)
                currentImageIndex+=1
            }
            navController.navigate("race_detail/$meetingKey/$sessionKey/$durationMinutes") {
                popUpTo("race_countdown") { inclusive = true }
            }

        }

        if (currentImageIndex < images.size) {
            Image(
                painter = painterResource(id = images[currentImageIndex]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            )
        }
    }
}
