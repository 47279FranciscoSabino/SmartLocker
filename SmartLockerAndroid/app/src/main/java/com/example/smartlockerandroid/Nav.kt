package com.example.smartlockerandroid

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartlockerandroid.data.RetrofitInstance
import com.example.smartlockerandroid.ui.screens.HistoryScreen
import com.example.smartlockerandroid.ui.screens.HomePageScreen
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
                onClickLogIn = { navController.navigate("main") }
            )
        }

        composable("main"){
            val userI = 1
            MainScreen(
                onInfoRequest = { activity?.finish() },
                onProfileRequest = { navController.navigate("profile/$userI") },
                onHistoryRequest = { userId -> navController.navigate("history/$userId") },
                onTradeInfoRequest = { tradeId -> navController.navigate("tradeInfo/$tradeId") },
                onNewRequest = { navController.navigate("scan_qr") },
                userId = userI
            )
        }

        composable("history/{userId}"){
            val userId = it.arguments?.getString("userId")?.toIntOrNull()
            HistoryScreen(
                onInfoRequest = { activity?.finish() },
                onProfileRequest = { navController.navigate("profile/$userId") },
                onBackRequest = { navController.popBackStack() },
                onClickRequest = { tradeId -> navController.navigate("tradeInfo/$tradeId") },
                historyService = RetrofitInstance.historyService,
                lockerService = RetrofitInstance.lockerService,
                moduleService = RetrofitInstance.moduleService,
                userId = userId!!
            )
        }

        composable("tradeInfo/{tradeId}") {
            val tradeId = it.arguments?.getString("tradeId")?.toIntOrNull()
            TradeScreen(
                onInfoRequest = { activity?.finish() },
                onProfileRequest = { navController.navigate("profile/1") },
                onBackRequest = { navController.popBackStack() },
                tradeService = RetrofitInstance.tradeService,
                userService = RetrofitInstance.userService,
                lockerService = RetrofitInstance.lockerService,
                moduleService = RetrofitInstance.moduleService,
                tradeId = tradeId!!
            )
        }

        composable("scan_qr") {
            QrScanScreen(
                onScanResult = { hash -> },
                lockerService = RetrofitInstance.lockerService,
                tradeService = RetrofitInstance.tradeService,
                scanService = RetrofitInstance.scanService,
                friendsService = RetrofitInstance.friendsService,
                currentUserId = 1,
                onSuccessNavigate = {}
            )
        }

        composable("profile/{userId}") {
            val userId = it.arguments?.getString("userId")?.toIntOrNull()
            ProfileScreen(
                onInfoRequest = { activity?.finish() },
                onBackRequest = { navController.popBackStack() },
                onSettingsClick = { navController.navigate("settings/${userId}") },
                onLogoutClick = { activity?.finish() },
                userService = RetrofitInstance.userService,
                friendsService = RetrofitInstance.friendsService,
                userId = userId!!,
            )
        }

        composable("settings/{userId}"){
            val userId = it.arguments?.getString("userId")?.toIntOrNull()
            SettingsScreen(
                onBackRequest = { navController.popBackStack() },
                userService = RetrofitInstance.userService,
                userId = userId!!
            )
        }

        composable("info") {

        }
    }
}