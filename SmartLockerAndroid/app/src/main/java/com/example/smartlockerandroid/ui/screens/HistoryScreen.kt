package com.example.smartlockerandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.ui.theme.MyBlue
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.HistoryViewModel


@Composable
fun HistoryScreen(
    onInfoRequest: (() -> Unit)? = null,
    onProfileRequest: (() -> Unit)? = null,
    onBackRequest: (() -> Unit)? = null,
    service: HistoryService,
    userId: Int,
){
    val viewModel: HistoryViewModel = viewModel(
        factory = viewModelInit { HistoryViewModel(service, userId) }
    )
    val pastTrades = viewModel.pastTrades
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
                    if (pastTrades.size > 0) {
                        Box(modifier = Modifier.padding(10.dp)) {
                            Column {
                                pastTrades.forEach {
                                    Box(modifier = Modifier.background(MyBlue).size(50.dp))
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