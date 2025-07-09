package com.example.smartlockerandroid.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.data.service.HistoryService
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ModuleService
import com.example.smartlockerandroid.ui.components.history.HistoryTile
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.HistoryViewModel


@Composable
fun HistoryScreen(
    onInfoRequest: (() -> Unit)? = null,
    onProfileRequest: (() -> Unit)? = null,
    onBackRequest: (() -> Unit)? = null,
    onClickRequest: (Int) -> Unit = {},
    historyService: HistoryService,
    lockerService: LockerService,
    moduleService: ModuleService,
    userId: Int,
){
    val viewModel: HistoryViewModel = viewModel(
        factory = viewModelInit { HistoryViewModel(historyService, lockerService, moduleService, userId) }
    )
    val pastTrades = viewModel.pastTrades
    val locker = viewModel.tradesInfo
    val module = viewModel.module
    val loading = viewModel.isLoading
    val error = viewModel.errorMessage


    Scaffold(
        topBar = {
            TopBar(
                onInfoRequest = onInfoRequest,
                onProfileRequest = onProfileRequest,
                onBackRequest = onBackRequest
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            when {
                loading -> {
                    CircularProgressIndicator()
                }
                error != null -> {
                    Text("Error: $error")
                }
                else -> {
                    if (pastTrades.isNotEmpty()) {
                        Box(modifier = Modifier.padding(1.dp)) {
                            Column {
                                pastTrades.forEach { trade ->
                                    HistoryTile(
                                        location =  module.find { it.id == locker.find { it.id == trade.lockerId }?.module}?.locName.toString(),
                                        date = trade.startDate,
                                        onClick = {onClickRequest(trade.id)}
                                    )
                                }
                            }
                        }
                    } else {
                        Text("Your history is clear")
                    }
                }
            }
        }
    }
}

@Preview(showBackground  = true)
@Composable
fun HistoryPreview() {
    //HistoryScreen()
}