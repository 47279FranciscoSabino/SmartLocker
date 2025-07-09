package com.example.smartlockerandroid.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.data.service.HistoryService
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ModuleService
import com.example.smartlockerandroid.ui.components.history.HistoryButton
import com.example.smartlockerandroid.ui.components.trade.TradeTile
import com.example.smartlockerandroid.ui.theme.MyBlue
import com.example.smartlockerandroid.ui.theme.MyBlue2
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.MyTradesViewModel

@Composable
fun MyTradesScreen(
    onClickRequest: (Int) -> Unit = {},
    onHistoryRequest: (Int) -> Unit ={},
    historyService: HistoryService,
    lockerService: LockerService,
    moduleService: ModuleService,
    userId: Int

) {
    val myTradesViewModel: MyTradesViewModel = viewModel(
        factory = viewModelInit { MyTradesViewModel(historyService, lockerService, moduleService, userId) }
    )

    val receiver = myTradesViewModel.tradesToWithdraw
    val lockerReceiver = myTradesViewModel.receiverLocker
    val moduleReceiver = myTradesViewModel.receiverLockerModule

    val sender = myTradesViewModel.tradesPending
    val lockerSender = myTradesViewModel.senderLocker
    val moduleSender = myTradesViewModel.senderLockerModule

    val loading = myTradesViewModel.isLoading
    val error = myTradesViewModel.errorMessage

    LaunchedEffect(Unit) {
        myTradesViewModel.loadReceive()
        myTradesViewModel.loadSend()
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(receiver) {
        var count = 0
        if (receiver.isNotEmpty()) {
            for (trade in receiver) {
                if(!trade.read) count++
            }
            if(count >0) snackbarHostState.showSnackbar("You have $count trades to withdraw.")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
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
                    if (receiver.isNotEmpty()) {
                        Text("To Pick Up")
                        Column(Modifier.padding(10.dp).weight(3f)) {
                            receiver.forEach { trade ->
                                Box(modifier = Modifier.background(MyBlue)){
                                    TradeTile(
                                        location = moduleReceiver.find { it.id == lockerReceiver.find { it.id == trade.lockerId }?.module}?.locName.toString(),
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

                    if (sender.isNotEmpty()) {
                        Text("Drop")
                        Column(Modifier.padding(10.dp).weight(3f)) {
                            sender.forEach { trade ->
                                Box(modifier = Modifier.background(MyBlue2)){
                                    TradeTile(
                                        location = moduleSender.find { it.id == lockerSender.find { it.id == trade.lockerId }?.module  }?.locName.toString(),
                                        date = trade.startDate,
                                        onClick = {onClickRequest(trade.id)}
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    HistoryButton { onHistoryRequest(userId) }
                }
            }
        }
    }
}

@Preview(showBackground  = true)
@Composable
fun MyLockersPreview() {
    //MyTradesScreen()
}