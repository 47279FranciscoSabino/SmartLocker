package com.example.smartlockerandroid

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartlockerandroid.data.RetrofitInstance
import com.example.smartlockerandroid.ui.screens.HistoryScreen
import com.example.smartlockerandroid.ui.screens.home.HomePageScreen
import com.example.smartlockerandroid.ui.screens.QrScanScreen
import com.example.smartlockerandroid.ui.screens.profile.ProfileScreen
import com.example.smartlockerandroid.ui.screens.mainscreen.MainScreen
import com.example.smartlockerandroid.ui.screens.TradeScreen
import com.example.smartlockerandroid.ui.screens.profile.SettingsScreen

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
                onClickLogIn = { navController.navigate("main") },
                userService = RetrofitInstance.userService
            )
        }

        composable("main"){
            MainScreen(
                onInfoRequest = { activity?.finish() },
                onProfileRequest = { navController.navigate("profile") },
                onHistoryRequest = { navController.navigate("history") },
                onTradeInfoRequest = { tradeId -> navController.navigate("tradeInfo/$tradeId") },
                onNewRequest = { navController.navigate("scan_qr") }
            )
        }

        composable("history"){
            HistoryScreen(
                onInfoRequest = { activity?.finish() },
                onProfileRequest = { navController.navigate("profile") },
                onBackRequest = { navController.popBackStack() },
                onClickRequest = { tradeId -> navController.navigate("tradeInfo/$tradeId") },
                historyService = RetrofitInstance.historyService
            )
        }

        composable("tradeInfo/{tradeId}") {
            val tradeId = it.arguments?.getString("tradeId")?.toIntOrNull()
            TradeScreen(
                onInfoRequest = { activity?.finish() },
                onProfileRequest = { navController.navigate("profile") },
                onBackRequest = { navController.popBackStack() },
                tradeService = RetrofitInstance.tradeService,
                userService = RetrofitInstance.userService,
                tradeId = tradeId!!
            )
        }

        composable("scan_qr") {
            QrScanScreen(
                onBackRequest = { navController.popBackStack() },
                tradeService = RetrofitInstance.tradeService,
                scanService = RetrofitInstance.scanService,
                friendsService = RetrofitInstance.friendsService
            )
        }

        composable("profile") {
            ProfileScreen(
                onInfoRequest = { activity?.finish() },
                onBackRequest = { navController.popBackStack() },
                onSettingsClick = { navController.navigate("settings") },
                onLogoutClick = { navController.navigate("home") },
                userService = RetrofitInstance.userService,
                friendsService = RetrofitInstance.friendsService,
            )
        }

        composable("settings"){
            SettingsScreen(
                onBackRequest = { navController.popBackStack() },
                userService = RetrofitInstance.userService
            )
        }

        composable("info") {

        }
    }
}