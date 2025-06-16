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
import com.example.smartlockerandroid.ui.screens.mainscreen.MainScreen
import com.example.smartlockerandroid.ui.screens.mainscreen.NewTradeScreen
import com.example.smartlockerandroid.ui.screens.TradeScreen

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
                onHistoryRequest = {userId -> navController.navigate("history/$userId") },
                onTradeInfoRequest = { tradeId -> navController.navigate("tradeInfo/$tradeId") },
                onNewRequest =  { activity?.finish() }
            )
        }

        composable("history/{userId}"){
            val userId = it.arguments?.getString("userId")?.toIntOrNull()
            HistoryScreen(
                onInfoRequest = { activity?.finish() },
                onProfileRequest = { activity?.finish() },
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
                onProfileRequest = { activity?.finish() },
                onBackRequest = { navController.popBackStack() },
                tradeService = RetrofitInstance.tradeService,
                userService = RetrofitInstance.userService,
                lockerService = RetrofitInstance.lockerService,
                moduleService = RetrofitInstance.moduleService,
                tradeId = tradeId!!
            )
        }

        composable("new_trade"){
            NewTradeScreen(
                onClickRequest = { activity?.finish() },
                moduleService = RetrofitInstance.moduleService,
            )
        }

        composable("profile") {

        }

        composable("info") {

        }

        composable("send") {

        }

    }
}