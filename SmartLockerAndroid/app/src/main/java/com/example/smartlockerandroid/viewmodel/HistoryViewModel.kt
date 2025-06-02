package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.trade.TradeDTO
import com.example.smartlockerandroid.data.service.HistoryService
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val service: HistoryService,
    private val userId: Int // Provide this from auth/session
) : ViewModel() {

    var pastTrades by mutableStateOf<List<TradeDTO>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadTrades()
    }

    fun loadTrades() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val past = service.getUserHistory(userId)

                // Filter out ended trades (you can adjust based on your data model)
                pastTrades = past.filter { it.status != "PENDING" }

            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }
}

