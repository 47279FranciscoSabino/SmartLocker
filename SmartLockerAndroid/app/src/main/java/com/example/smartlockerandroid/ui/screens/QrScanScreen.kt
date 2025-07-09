package com.example.smartlockerandroid.ui.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.data.service.FriendsService
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ScanService
import com.example.smartlockerandroid.data.service.TradeService
import com.example.smartlockerandroid.ui.components.CreateTradeFriend
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.ui.theme.MyBlue
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.QrScanViewModel
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
@Composable
fun QrScanScreen(
    onScanResult: (String) -> Unit,
    lockerService: LockerService,
    tradeService: TradeService,
    scanService: ScanService,
    friendsService: FriendsService,
    currentUserId: Int,
    onSuccessNavigate: () -> Unit
) {
    val scannedValue = remember { mutableStateOf<String?>(null) }

    val viewModel: QrScanViewModel? = if (scannedValue.value != null) {
        viewModel(
            factory = viewModelInit {
                QrScanViewModel(
                    scanService,
                    lockerService,
                    tradeService,
                    friendsService,
                    scannedValue.value
                )
            }
        )
    } else null

    val locker = viewModel?.locker

    val friends = viewModel?.friends


    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanQRCode(),
        onResult = { result ->
            when (result) {
                is QRResult.QRSuccess -> {
                    Log.i("QrScanScreen", "Got scanned QR: ${result.content.rawValue}")
                    scannedValue.value = result.content.rawValue
                }
                is QRResult.QRError -> {
                    Log.e("QrScanScreen", "QR Error: ${result.exception}")
                }
                QRResult.QRUserCanceled -> {
                    Log.i("QrScanScreen", "User canceled scanning.")
                }
                QRResult.QRMissingPermission -> {
                    Log.w("QrScanScreen", "Missing camera permissions.")
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        scanLauncher.launch(null)
    }

    val showConfirmDialog = remember { mutableStateOf(false) }
    val isPickup = remember { mutableStateOf(false) }

    val showFriendsList = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar()
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray)
        ) {
            if (showFriendsList.value && friends!=null){
                Box(Modifier.weight(3f)) {
                    if (friends.any { it.status == "ACCEPTED" }) {
                        Column(Modifier.padding(10.dp)) {
                            friends.forEach { friend ->
                                if (friend.status == "ACCEPTED") {
                                    Box(modifier = Modifier
                                        .background(MyBlue)
                                        .clickable {
                                            viewModel.createTrade(currentUserId, friend.user, locker!!.id)
                                        }
                                    ) {
                                        CreateTradeFriend(
                                            name = friend.name,
                                            days = friend.days
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                            }
                        }
                    } else {
                        Text("You dont have any friends );")
                    }
                }
            }
            viewModel?.let {
                val loading = it.isLoading
                val error = it.errorMessage

                when {
                    loading -> {
                        CircularProgressIndicator()
                    }

                    error != null -> {
                        Text("Error: $error")
                    }

                    locker != null -> {
                        if (locker.active) {
                            Text("Locker ocupado. Confirmar levantamento?")
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = {
                                isPickup.value = true
                                showConfirmDialog.value = true
                            }) {
                                Text("Confirmar levantamento")
                            }
                        } else {
                            Text("Locker livre. Criar nova troca?")
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = {
                                isPickup.value = false
                                showConfirmDialog.value = true
                            }) {
                                Text("Criar troca")
                            }
                        }
                    }
                }
            }
        }

        if (showConfirmDialog.value) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog.value = false },
                title = {
                    Text(
                        if (isPickup.value)
                            "Confirmar levantamento"
                        else
                            "Confirmar criação de troca"
                    )
                },
                text = {
                    Text(
                        if (isPickup.value)
                            "Tem a certeza que pretende levantar o item do locker?"
                        else
                            "Deseja criar uma nova troca neste locker?"
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        showConfirmDialog.value = false
                        if (isPickup.value) {
                            viewModel?.confirmPickup(currentUserId, locker!!)
                        } else {
                            viewModel?.loadFriends(currentUserId)
                            showFriendsList.value = true
                        }
                        onSuccessNavigate()
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showConfirmDialog.value = false
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}