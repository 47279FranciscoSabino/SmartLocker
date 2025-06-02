package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.trade.CreateTradeRequest
import com.example.smartlockerandroid.data.model.trade.TradeDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TradeService {
    @GET("/trade/{id}")
    suspend fun getTradeById(@Path("id") id: Int): TradeDTO

    @POST("/trade")
    suspend fun createTrade(@Body input: CreateTradeRequest)
}