package com.example.smartlockerandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.data.RetrofitInstance.lockerService
import com.example.smartlockerandroid.data.service.HistoryService
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.ui.components.HistoryButton
import com.example.smartlockerandroid.ui.components.TradeTile
import com.example.smartlockerandroid.ui.theme.MyBlue
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.LockerLoader
import com.example.smartlockerandroid.viewmodel.LockerViewModel
import com.example.smartlockerandroid.viewmodel.MyTradesViewModel

@Composable
fun MyTradesScreen(
    service: HistoryService,
    userId: Int,
    onClickRequest: () -> Unit = {}
) {
    val viewModel: MyTradesViewModel = viewModel(
        factory = viewModelInit { MyTradesViewModel(service, userId) }
    )
    val receiver = viewModel.waitingTrades
    val sender = viewModel.myPendingTrades
    val loading = viewModel.isLoading
    val error = viewModel.errorMessage

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            loading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text("Error: $error")
            }
            else -> {
                if (receiver.isNotEmpty()) {
                    Text("Waiting...")
                    Column(Modifier.padding(10.dp)) {
                        receiver.forEach { trade ->
                            if (trade.lockerId != null){
                                LockerLoader(trade.lockerId, lockerService){ locker ->
                                    if (locker != null) {
                                        /*
                                        TradeTile(
                                            userId = trade.senderId,
                                            lockerLocation = locker.location,
                                            onClick = { /* navigate */ }
                                        )

                                         */
                                    } else{
                                        Box(modifier = Modifier.background(MyBlue).size(50.dp))
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Text("No lockers to withdraw")
                }

                Spacer(modifier = Modifier.height(50.dp))

                if (sender.isNotEmpty()) {
                    Text("Pending...")
                    Column(Modifier.padding(10.dp)) {
                        sender.forEach {
                            Box(modifier = Modifier.background(MyBlue).size(50.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))

                HistoryButton { onClickRequest() }
            }
        }
    }
}

@Preview(showBackground  = true)
@Composable
fun MyLockersPreview() {
    //MyTradesScreen()
}

/*
@Composable
fun LockerListScreen(viewModel: LockerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val lockers by viewModel.lockers.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn {
            items(lockers) { locker ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Handle click later
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Locker ID: ${locker.id}")
                        Text("QR: ${locker.qr}")
                        Text("Status: ${locker.status}")
                        Text("Active: ${locker.active}")
                    }
                }
            }
        }
    }
}

 */