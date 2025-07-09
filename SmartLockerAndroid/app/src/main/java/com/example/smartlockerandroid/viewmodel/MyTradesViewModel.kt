package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.locker.output.LockerDTO
import com.example.smartlockerandroid.data.model.module.output.ModuleDTO
import com.example.smartlockerandroid.data.model.trade.output.TradeDTO
import com.example.smartlockerandroid.data.service.HistoryService
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ModuleService
import kotlinx.coroutines.launch

class MyTradesViewModel(
    private val historyService: HistoryService,
    private val lockerService: LockerService,
    private val moduleService: ModuleService,
    private val userId: Int
) : ViewModel() {

    var tradesToWithdraw by mutableStateOf<List<TradeDTO>>(emptyList())
        private set
    var receiverLocker by mutableStateOf<List<LockerDTO>>(emptyList())
        private set
    var receiverLockerModule by mutableStateOf<List<ModuleDTO>>(emptyList())
        private set


    var tradesPending by mutableStateOf<List<TradeDTO>>(emptyList())
        private set
    var senderLocker by mutableStateOf<List<LockerDTO>>(emptyList())
        private set
    var senderLockerModule by mutableStateOf<List<ModuleDTO>>(emptyList())
        private set


    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadReceive()
        loadSend()
    }

    fun loadReceive(){
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val received = historyService.getByReceiver(userId) // Trades where I'm the receiver

                tradesToWithdraw = received.filter { it.status == "PENDING" }
                receiverLocker= tradesToWithdraw.map { lockerService.getLockerById(it.lockerId) }
                receiverLockerModule = receiverLocker.map { moduleService.getModule(it.module) }

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

                val sent = historyService.getBySender(userId) // Trades I created

                tradesPending = sent.filter { it.status == "PENDING" }
                senderLocker = tradesPending.map { lockerService.getLockerById(it.lockerId) }
                senderLockerModule = senderLocker.map { moduleService.getModule(it.module) }

            } catch (e: Exception) {
                errorMessage = e.message ?: "Error on send trades request"
            } finally {
                isLoading = false
            }
        }
    }
}