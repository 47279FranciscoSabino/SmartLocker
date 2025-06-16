package com.example.smartlockerandroid.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.data.service.ModuleService
import com.example.smartlockerandroid.ui.components.MapBox
import com.example.smartlockerandroid.ui.components.NewTradeButton
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.NewTradeViewModel
import org.osmdroid.util.GeoPoint

@Composable
fun NewTradeScreen(
    onClickRequest: () -> Unit = { },
    moduleService: ModuleService,

    ){

    // get geo location
    // hardcoded -> 38.765970151954996, -9.118521289095881 -- (vale do silencio)

    val center = GeoPoint(38.765970151954996, -9.118521289095881)

    val newTradeViewModel: NewTradeViewModel = viewModel(
        factory = viewModelInit { NewTradeViewModel(moduleService, center) }
    )

    val modules = newTradeViewModel.modules

    val loading = newTradeViewModel.isLoading
    val error = newTradeViewModel.errorMessage

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            loading -> {
                CircularProgressIndicator(
                    color = Color.Transparent
                )
            }

            error != null -> {
                Text("Error: $error")
            }

            else -> {
                Spacer(modifier = Modifier.height(80.dp))
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    MapBox(
                        modules,
                        center
                    )
                }
                Spacer(modifier = Modifier.height(80.dp))
                Box {
                    NewTradeButton { onClickRequest() }
                }
            }
        }
    }
}

