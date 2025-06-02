package com.example.smartlockerandroid.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartlockerandroid.data.RetrofitInstance
import com.example.smartlockerandroid.ui.components.HistoryButton
import com.example.smartlockerandroid.ui.screens.HistoryScreen
import com.example.smartlockerandroid.ui.screens.HomePageScreen
import com.example.smartlockerandroid.ui.screens.MainScreen
import com.example.smartlockerandroid.ui.screens.NewTradeScreen

@Composable
fun Nav(
    navController: NavHostController
) {
    val context = LocalContext.current
    val activity = context as? Activity

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePageScreen(
                onInfoRequest = { activity?.finish() },
                onClickLogIn = { navController.navigate("main") }
            )
        }

        composable("main"){
            MainScreen(
                onInfoRequest = { activity?.finish() },
                onProfileRequest = { activity?.finish() },
                onHistoryRequest = { navController.navigate("history")},
                onNewRequest =  { activity?.finish() }
            )
        }

        composable("history"){
            HistoryScreen(
                onInfoRequest = { activity?.finish() },
                onProfileRequest = { activity?.finish() },
                onBackRequest = { activity?.finish() },
                service =  RetrofitInstance.historyService, // Replace with your actual service instance
                userId = 1,
            )
        }

        /*
        composable("new_trade"){
            NewTradeScreen(
                onClickLogIn =  { activity?.finish() }
            )
        }

         */
    }
}