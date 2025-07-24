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
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePageScreen(
                onClickLogIn = { navController.navigate("main") },
                userService = RetrofitInstance.userService
            )
        }

        composable("main"){
            MainScreen(
                onProfileRequest = { navController.navigate("profile") },
                onHistoryRequest = { navController.navigate("history") },
                onTradeInfoRequest = { tradeId -> navController.navigate("tradeInfo/$tradeId") },
                onNewRequest = { navController.navigate("scan_qr") },
                moduleService = RetrofitInstance.moduleService,
                tradeService = RetrofitInstance.tradeService,
                historyService = RetrofitInstance.historyService
            )
        }

        composable("history"){
            HistoryScreen(
                onProfileRequest = { navController.navigate("profile") },
                onBackRequest = { navController.popBackStack() },
                onClickRequest = { tradeId -> navController.navigate("tradeInfo/$tradeId") },
                historyService = RetrofitInstance.historyService
            )
        }

        composable("tradeInfo/{tradeId}") {
            val tradeId = it.arguments?.getString("tradeId")?.toIntOrNull()
            TradeScreen(
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
    }
}