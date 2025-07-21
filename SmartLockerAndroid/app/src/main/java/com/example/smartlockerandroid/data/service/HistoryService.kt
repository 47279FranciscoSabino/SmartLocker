package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.trade.output.TradeDTO
import com.example.smartlockerandroid.data.model.trade.output.TradeInfoDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HistoryService {
    @GET("user/{id}/history")
    suspend fun getUserHistory(@Path("id") id: Int): List<TradeDTO>

    @GET("receiver/{id}")
    suspend fun getByReceiver(@Path("id") id: Int): List<TradeDTO>

    @GET("sender/{id}")
    suspend fun getBySender(@Path("id") id: Int): List<TradeDTO>



    @GET("history")
    suspend fun getUserFullHistory(@Header("Authorization") token: String): List<TradeInfoDTO>

    @GET("user/sent")
    suspend fun senderHistory(@Header("Authorization") token: String): List<TradeInfoDTO>

    @GET("user/received")
    suspend fun receiverHistory(@Header("Authorization") token: String): List<TradeInfoDTO>

}

