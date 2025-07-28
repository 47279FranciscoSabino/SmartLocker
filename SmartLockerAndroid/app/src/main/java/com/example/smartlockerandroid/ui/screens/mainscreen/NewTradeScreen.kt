package com.example.smartlockerandroid.ui.screens.mainscreen

import android.Manifest
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.data.service.ModuleService
import com.example.smartlockerandroid.ui.components.main.MapBox
import com.example.smartlockerandroid.ui.components.main.NewTradeButton
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.NewTradeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import org.osmdroid.util.GeoPoint

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NewTradeScreen(
    onClickRequest: () -> Unit = { },
    moduleService: ModuleService,
) {
    val context = LocalContext.current
    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val location = remember { mutableStateOf<GeoPoint?>(null) }
    val client = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Request permissions when screen launches
    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
    }

    // Only proceed when at least one permission is granted
    LaunchedEffect(permissionsState.permissions) {
        if (permissionsState.permissions.any { it.status.isGranted }) {
            val request = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setDurationMillis(5000)
                .build()

            client.getCurrentLocation(request, null)
                .addOnSuccessListener { loc ->
                    loc?.let {
                        location.value = GeoPoint(it.latitude, it.longitude)
                    }
                }
                .addOnFailureListener {
                    Log.e("NewTradeScreen", "Failed to get current location", it)
                }
        }
    }

    val center = GeoPoint(0.0, 0.0)

    val newTradeViewModel: NewTradeViewModel = viewModel(
        factory = viewModelInit { NewTradeViewModel(moduleService) }
    )

    var modules = newTradeViewModel.modules
    val loading = newTradeViewModel.isLoading
    val error = newTradeViewModel.errorMessage

    LaunchedEffect(location.value) {
        location.value?.let {
            newTradeViewModel.updateLocation(it)
        }
        modules = newTradeViewModel.modules
    }

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
                Spacer(modifier = Modifier.height(80.dp))
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    MapBox(
                        modules,
                        location.value ?: center
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
