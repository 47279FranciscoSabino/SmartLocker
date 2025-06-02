package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.trade.TradeDTO
import com.example.smartlockerandroid.data.service.HistoryService
import kotlinx.coroutines.launch

class MyTradesViewModel(
    private val service: HistoryService,
    private val userId: Int // Provide this from auth/session
) : ViewModel() {

    var waitingTrades by mutableStateOf<List<TradeDTO>>(emptyList())
        private set

    var myPendingTrades by mutableStateOf<List<TradeDTO>>(emptyList())
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

                val received = service.getBySender(userId) // Trades where I'm the receiver
                val created = service.getByReceiver(userId) // Trades I created

                // Filter out ended trades (you can adjust based on your data model)
                waitingTrades = received.filter { it.status == "PENDING" }
                myPendingTrades = created.filter { it.status == "PENDING" }

            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }
}