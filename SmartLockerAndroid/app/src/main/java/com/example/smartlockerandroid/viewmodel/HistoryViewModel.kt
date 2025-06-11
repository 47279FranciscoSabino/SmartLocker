package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.locker.LockerDTO
import com.example.smartlockerandroid.data.model.module.ModuleDTO
import com.example.smartlockerandroid.data.model.trade.TradeDTO
import com.example.smartlockerandroid.data.service.HistoryService
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ModuleService
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyService: HistoryService,
    private val lockerService: LockerService,
    private val moduleService: ModuleService,
    private val userId: Int
) : ViewModel() {

    var pastTrades by mutableStateOf<List<TradeDTO>>(emptyList())
        private set

    var tradesInfo by mutableStateOf<List<LockerDTO>>(emptyList())
        private set

    var module by mutableStateOf<List<ModuleDTO>>(emptyList())
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

                val past = historyService.getUserHistory(userId)

                pastTrades = past.filter { it.status != "PENDING" }

                tradesInfo = pastTrades.map { lockerService.getLockerById(it.lockerId) }
                module = tradesInfo.map { moduleService.getModule(it.module) }

            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }
}

