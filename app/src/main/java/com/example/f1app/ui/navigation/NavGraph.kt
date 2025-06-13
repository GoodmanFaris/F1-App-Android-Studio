package com.example.f1app.ui.navigation

import android.R.attr.type
import androidx.compose.runtime.Composable
import com.example.f1app.ui.screen.raceList.RaceListScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.f1app.ui.screen.countdown.RaceCountdownScreen
import com.example.f1app.ui.screen.raceDetail.RaceDetailScreen
import com.example.f1app.ui.screen.splashScreen.SplashScreen



@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController)
        }

        composable("race_list") {
            RaceListScreen(navController)
        }

        composable(
            "race_countdown/{meetingKey}/{sessionKey}/{durationMinutes}",
            arguments = listOf(
                navArgument("meetingKey") { type = NavType.IntType },
                navArgument("sessionKey") { type = NavType.IntType },
                navArgument("durationMinutes") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val meetingKey = backStackEntry.arguments?.getInt("meetingKey") ?: 0
            val sessionKey = backStackEntry.arguments?.getInt("sessionKey") ?: 0
            val durationMinutes = backStackEntry.arguments?.getInt("durationMinutes") ?: 1

            RaceCountdownScreen(navController, meetingKey, sessionKey, durationMinutes)
        }

        composable(
            "race_detail/{meetingKey}/{sessionKey}/{durationMinutes}",
            arguments = listOf(
                navArgument("meetingKey") { type = NavType.IntType },
                navArgument("sessionKey") { type = NavType.IntType },
                navArgument("durationMinutes") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val meetingKey = backStackEntry.arguments?.getInt("meetingKey") ?: 0
            val sessionKey = backStackEntry.arguments?.getInt("sessionKey") ?: 0
            val durationMinutes = backStackEntry.arguments?.getInt("durationMinutes") ?: 1

            RaceDetailScreen(navController, meetingKey, sessionKey, durationMinutes)
        }
    }
}

