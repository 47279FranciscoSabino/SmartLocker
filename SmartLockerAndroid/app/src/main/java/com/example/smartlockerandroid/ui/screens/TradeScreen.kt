package com.example.smartlockerandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.TokenProvider
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ModuleService
import com.example.smartlockerandroid.data.service.TradeService
import com.example.smartlockerandroid.data.service.UserService
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.ui.components.trade.TradeCard
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.TradeViewModel

@Composable
fun TradeScreen(
    onProfileRequest: (() -> Unit)? = null,
    onBackRequest: (() -> Unit)? = null,
    tradeService: TradeService,
    userService: UserService,
    tradeId: Int
){
    val context = LocalContext.current
    val token = remember { mutableStateOf<String?>(null) }
    token.value = TokenProvider.getToken(context)

    val tradeViewModel: TradeViewModel = viewModel(
        factory = viewModelInit { TradeViewModel(tradeService, userService, tradeId, token.value) }
    )

    val trade = tradeViewModel.trade
    val sender = tradeViewModel.sender
    val receiver = tradeViewModel.receiver

    val loading = tradeViewModel.isLoading
    val error = tradeViewModel.errorMessage

    Scaffold(
        topBar = {
            TopBar(
                onProfileRequest = onProfileRequest,
                onBackRequest = onBackRequest
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(it).background(Color.LightGray)
        ) {
            when {
                loading -> {
                    CircularProgressIndicator()
                }

                error != null -> {
                    Text("Error: $error")
                }

                else -> {
                    if (trade != null && sender !=null && receiver != null) {
                        TradeCard(trade, sender.username, receiver.username)
                    }
                }
            }
        }
    }
}