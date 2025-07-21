package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.trade.output.TradeInfoDTO
import com.example.smartlockerandroid.data.service.HistoryService
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyService: HistoryService,
    private val token: String?
) : ViewModel() {

    var pastTrades by mutableStateOf<List<TradeInfoDTO>>(emptyList())
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
                val bearerToken = "Bearer ${token}"
                pastTrades = historyService.getUserFullHistory(bearerToken)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }
}

