package com.example.smartlockerandroid.ui.screens.mainscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartlockerandroid.ui.components.history.HistoryButton
import com.example.smartlockerandroid.ui.components.trade.TradeTile
import com.example.smartlockerandroid.ui.theme.MyBlue
import com.example.smartlockerandroid.ui.theme.MyBlue2
import com.example.smartlockerandroid.viewmodel.MyTradesViewModel

@Composable
fun MyTradesScreen(
    onClickRequest: (Int) -> Unit = {},
    onHistoryRequest: () -> Unit,
    viewModel: MyTradesViewModel
) {
    val received = viewModel.pendingTrades

    val sent = viewModel.sentTrades

    val loading = viewModel.isLoading
    val error = viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.loadReceive()
        viewModel.loadSend()
        viewModel.updateTrade()

        Log.i("we", received.toString())
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        when {
            loading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text("Error: $error")
            }
            else -> {
                if (received.isNotEmpty()) {
                    Text("To Pick Up")
                    Column(Modifier.padding(10.dp).weight(3f)) {
                        received.forEach { trade ->
                            Box(modifier = Modifier.background(MyBlue)){
                                TradeTile(
                                    location = trade.location,
                                    date = trade.startDate,
                                    receive = true,
                                    onClick = {onClickRequest(trade.id)}
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                } else {
                    Text("No trades to withdraw")
                }

                Spacer(modifier = Modifier.height(50.dp))

                if (sent.isNotEmpty()) {
                    Text("Drop")
                    Column(Modifier.padding(10.dp).weight(3f)) {
                        sent.forEach { trade ->
                            Box(modifier = Modifier.background(MyBlue2)){
                                TradeTile(
                                    location = trade.location,
                                    date = trade.startDate,
                                    onClick = {onClickRequest(trade.id)}
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                HistoryButton { onHistoryRequest() }
            }
        }
    }
}
