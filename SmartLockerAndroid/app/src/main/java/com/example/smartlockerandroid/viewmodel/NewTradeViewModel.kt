package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.module.ModuleAppDTO
import com.example.smartlockerandroid.data.model.module.ModuleDTO
import com.example.smartlockerandroid.data.service.ModuleService
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint


class NewTradeViewModel(
    private val moduleService: ModuleService,
    private val center: GeoPoint
) : ViewModel() {

    var modules by mutableStateOf<List<ModuleAppDTO>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadModules()
    }

    fun loadModules(){
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                modules = moduleService.getModulesByRadius(center.latitude, center.longitude, 50000.0)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Error on module request"
            } finally {
                isLoading = false
            }
        }
    }

}