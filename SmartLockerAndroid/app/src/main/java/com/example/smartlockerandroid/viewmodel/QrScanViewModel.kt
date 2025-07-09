package com.example.smartlockerandroid.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.friends.output.FriendDTO
import com.example.smartlockerandroid.data.model.hash.output.QrScanDTO
import com.example.smartlockerandroid.data.model.locker.output.LockerDTO
import com.example.smartlockerandroid.data.model.trade.input.CreateTradeRequest
import com.example.smartlockerandroid.data.model.trade.input.UpdateTradeRequest
import com.example.smartlockerandroid.data.service.FriendsService
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ScanService
import com.example.smartlockerandroid.data.service.TradeService
import com.example.smartlockerandroid.data.service.UserService
import kotlinx.coroutines.launch

class QrScanViewModel(
    private val scanService: ScanService,
    private val lockerService: LockerService,
    private val tradeService: TradeService,
    private val friendsService: FriendsService,
    private val h: String?
) : ViewModel() {

    var hash by mutableStateOf<QrScanDTO?>(null)
        private set

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

                hash = scanService.getHash(h)

                locker = lockerService.getLockerByHash(hash!!.hash)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to load valid hash"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadFriends(userId: Int) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                friends = friendsService.getAllFriends(userId)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to load user"
            } finally {
                isLoading = false
            }
        }
    }

    fun createTrade(userId: Int, friend: Int, locker:Int) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                Log.i("trade", "create")
                val input = CreateTradeRequest(userId, friend, locker)
                tradeService.createTrade(input)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Erro ao criar troca"
            } finally {
                isLoading = false
            }
        }
    }

    fun confirmPickup(userId: Int, locker:LockerDTO) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                Log.i("trade", "confirm")
                val trade = tradeService.getPendingTrade(locker.id)
                var status = "COMPLETED"
                if (userId == trade.senderId) status = "CANCELLED"
                val input = UpdateTradeRequest(false, status)
                tradeService.updateTrade(trade.id, input)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Erro ao confirmar levantamento"
            } finally {
                isLoading = false
            }
        }
    }
}