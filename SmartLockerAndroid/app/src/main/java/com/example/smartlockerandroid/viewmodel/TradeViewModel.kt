package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.module.output.ModuleDTO
import com.example.smartlockerandroid.data.model.trade.output.TradeDTO
import com.example.smartlockerandroid.data.model.trade.output.TradeInfoDTO
import com.example.smartlockerandroid.data.model.user.output.UserDTO
import com.example.smartlockerandroid.data.model.user.output.UserInfoDTO
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ModuleService
import com.example.smartlockerandroid.data.service.TradeService
import com.example.smartlockerandroid.data.service.UserService
import kotlinx.coroutines.launch

class TradeViewModel(
    private val tradeService: TradeService,
    private val userService: UserService,
    private val tradeId: Int,
    private val token: String?
) : ViewModel() {

    var trade by mutableStateOf<TradeInfoDTO?>(null)
        private set

    var sender by mutableStateOf<UserInfoDTO?>(null)
        private set
    var receiver by mutableStateOf<UserInfoDTO?>(null)
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

                val bearerToken = "Bearer ${token}"
                trade = tradeService.getTrade(tradeId, bearerToken )

                sender = userService.getUserInfo(trade!!.senderId, bearerToken)
                receiver = userService.getUserInfo(trade!!.receiverId, bearerToken)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Error on trades request"
            } finally {
                isLoading = false
            }
        }
    }
}