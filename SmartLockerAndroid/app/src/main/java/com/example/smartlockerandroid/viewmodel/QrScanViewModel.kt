package com.example.smartlockerandroid.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.friends.output.FriendDTO
import com.example.smartlockerandroid.data.model.locker.output.LockerDTO
import com.example.smartlockerandroid.data.model.trade.input.CreateTradeRequest
import com.example.smartlockerandroid.data.service.FriendsService
import com.example.smartlockerandroid.data.service.ScanService
import com.example.smartlockerandroid.data.service.TradeService
import kotlinx.coroutines.launch

class QrScanViewModel(
    private val scanService: ScanService,
    private val tradeService: TradeService,
    private val friendsService: FriendsService,
    private val h: String?,
    private val token: String?
) : ViewModel() {
    var locker by mutableStateOf<LockerDTO?>(null)
        private set

    var friends by mutableStateOf<List<FriendDTO>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                locker = scanService.scan(h, bearerToken)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to load valid hash"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadFriends() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                friends = friendsService.getUserFriends(bearerToken)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to load user"
            } finally {
                isLoading = false
            }
        }
    }

    fun createTrade(friend: Int, locker:Int) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                val input = CreateTradeRequest(friend, locker)
                tradeService.newTrade(input, bearerToken)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Erro ao criar troca"
            } finally {
                isLoading = false
            }
        }
    }

    fun confirmPickup(locker:LockerDTO) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                val bearerToken = "Bearer ${token}"

                tradeService.withdraw(locker.id, bearerToken)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Erro ao confirmar levantamento"
            } finally {
                isLoading = false
            }
        }
    }
}