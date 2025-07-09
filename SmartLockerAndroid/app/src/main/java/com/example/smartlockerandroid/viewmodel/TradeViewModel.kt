package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.module.output.ModuleDTO
import com.example.smartlockerandroid.data.model.trade.output.TradeDTO
import com.example.smartlockerandroid.data.model.user.output.UserDTO
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ModuleService
import com.example.smartlockerandroid.data.service.TradeService
import com.example.smartlockerandroid.data.service.UserService
import kotlinx.coroutines.launch

class TradeViewModel(
    private val tradeService: TradeService,
    private val lockerService: LockerService,
    private val moduleService: ModuleService,
    private val userService: UserService,
    private val tradeId: Int
) : ViewModel() {

    var trade by mutableStateOf<TradeDTO?>(null)
        private set
    var module by mutableStateOf<ModuleDTO?>(null)
        private set
    var sender by mutableStateOf<UserDTO?>(null)
        private set
    var receiver by mutableStateOf<UserDTO?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        load()
    }

    fun load(){
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                trade = tradeService.getTradeById(tradeId)
                val locker = lockerService.getLockerById(trade!!.lockerId)
                module = moduleService.getModule(locker.module)
                sender = userService.getUserById(trade!!.senderId)
                receiver = userService.getUserById(trade!!.receiverId)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Error on trades request"
            } finally {
                isLoading = false
            }
        }
    }
}