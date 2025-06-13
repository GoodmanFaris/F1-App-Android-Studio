package com.example.f1app.ui.component

import RacesViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.f1app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun F1TopBarNoSS(
    navController: NavHostController,
    viewModel: RacesViewModel = viewModel(),
    modifier: Modifier = Modifier
)  {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "F1 Logo",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(start = 20.dp)
                        .clickable {
                            navController.navigate("race_list") {
                                popUpTo("race_list") { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                    contentScale = ContentScale.Fit
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Red,
            titleContentColor = Color.White
        )
    )
}