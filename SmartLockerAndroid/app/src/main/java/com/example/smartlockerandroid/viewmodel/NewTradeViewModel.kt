package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.module.output.ModuleDTO
import com.example.smartlockerandroid.data.service.ModuleService
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint

class NewTradeViewModel(
    private val moduleService: ModuleService,
) : ViewModel() {

    var modules by mutableStateOf<List<ModuleDTO>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var currentLocation: GeoPoint? = null

    fun updateLocation(location: GeoPoint) {
        if (location != currentLocation) {
            currentLocation = location
            loadModules()
        }
    }

    private fun loadModules() {
        val loc = currentLocation ?: return
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                modules = moduleService.getMap(loc.latitude, loc.longitude, 50000.0)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Error on module request"
            } finally {
                isLoading = false
            }
        }
    }
}