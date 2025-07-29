package com.example.smartlockerandroid.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.TokenProvider
import com.example.smartlockerandroid.data.service.HistoryService
import com.example.smartlockerandroid.ui.components.history.HistoryTile
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.HistoryViewModel


@Composable
fun HistoryScreen(
    onProfileRequest: (() -> Unit)? = null,
    onBackRequest: (() -> Unit)? = null,
    onClickRequest: (Int) -> Unit = {},
    historyService: HistoryService
){
    val context = LocalContext.current
    val token = remember { mutableStateOf<String?>(null) }
    token.value = TokenProvider.getToken(context)

    val viewModel: HistoryViewModel = viewModel(
        factory = viewModelInit { HistoryViewModel(historyService, token.value) }
    )
    val pastTrades = viewModel.pastTrades
    val loading = viewModel.isLoading
    val error = viewModel.errorMessage


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
                            LazyColumn {
                                items(pastTrades) { trade ->
                                    HistoryTile(
                                        location = trade.location,
                                        date = trade.startDate,
                                        onClick = { onClickRequest(trade.id) }
                                    )
                                }
                            }
                        }
                    } else {
                        Text(stringResource(R.string.no_history))
                    }
                }
            }
        }
    }
}