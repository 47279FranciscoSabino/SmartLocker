package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.trade.input.UpdateTradeRequest
import com.example.smartlockerandroid.data.model.trade.output.TradeInfoDTO
import com.example.smartlockerandroid.data.service.HistoryService
import com.example.smartlockerandroid.data.service.TradeService
import kotlinx.coroutines.launch

class MyTradesViewModel(
    private val tradeService: TradeService,
    private val historyService: HistoryService,
    private val token: String
) : ViewModel() {
    var pendingTrades by mutableStateOf<List<TradeInfoDTO>>(emptyList())
        private set

    var sentTrades by mutableStateOf<List<TradeInfoDTO>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadReceive()
        loadSend()
        updateTrade()
    }

    fun loadReceive(){
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                pendingTrades = historyService.receiverHistory(bearerToken)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Error on receive trades request"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadSend(){
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                sentTrades = historyService.senderHistory(bearerToken)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Error on send trades request"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateTrade(){
        viewModelScope.launch {
            try{
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                pendingTrades.forEach{ it -> tradeService.updateTrade(it.id, UpdateTradeRequest(true) , bearerToken )}

            }catch (e: Exception) {
                errorMessage = e.message ?: "Error on send trades request"
            } finally {
                isLoading = false
            }
        }
    }
}