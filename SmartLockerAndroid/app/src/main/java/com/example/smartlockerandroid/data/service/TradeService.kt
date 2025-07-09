package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.trade.input.CreateTradeRequest
import com.example.smartlockerandroid.data.model.trade.input.UpdateTradeRequest
import com.example.smartlockerandroid.data.model.trade.output.TradeDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TradeService {
    @GET("trade/{id}")
    suspend fun getTradeById(@Path("id") id: Int): TradeDTO

    @GET("trade/pending/{locker}")
    suspend fun getPendingTrade(@Path("locker") locker: Int): TradeDTO

    @POST("trade")
    suspend fun createTrade(@Body input: CreateTradeRequest)

    @PUT("trade/{id}")
    suspend fun updateTrade(@Path("id") id: Int, @Body input: UpdateTradeRequest)
}