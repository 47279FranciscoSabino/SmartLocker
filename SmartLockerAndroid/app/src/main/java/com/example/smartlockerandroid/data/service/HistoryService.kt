package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.trade.output.TradeDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryService {
    @GET("user/{id}/history")
    suspend fun getUserHistory(@Path("id") id: Int): List<TradeDTO>

    @GET("receiver/{id}")
    suspend fun getByReceiver(@Path("id") id: Int): List<TradeDTO>

    @GET("sender/{id}")
    suspend fun getBySender(@Path("id") id: Int): List<TradeDTO>
}

