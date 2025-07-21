package com.example.smartlockerandroid.data.model.trade.input

data class CreateTradeRequest(
    val receiverId: Int,
    val lockerId: Int
)