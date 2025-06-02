package com.example.smartlockerandroid.data.model.trade

data class TradeDTO(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val lockerId: Int,
    val startDate: String,
    val endDate: String,
    val read: Boolean,
    val status: String
)