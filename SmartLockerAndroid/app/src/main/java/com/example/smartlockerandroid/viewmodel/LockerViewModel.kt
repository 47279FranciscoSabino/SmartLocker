package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.locker.LockerDTO
import com.example.smartlockerandroid.data.service.LockerService
import kotlinx.coroutines.launch

class LockerViewModel(
    private val service: LockerService,
    private val lockerId: Int
) : ViewModel() {

    var locker by mutableStateOf<LockerDTO?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadLocker()
    }

    fun loadLocker() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val response = service.getLockerById(lockerId)
                locker = response

            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }
}