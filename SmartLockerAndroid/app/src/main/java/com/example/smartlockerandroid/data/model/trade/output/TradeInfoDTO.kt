package com.example.smartlockerandroid.data.model.trade.output

data class TradeInfoDTO(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val lockerId: Int,
    val startDate: String,
    val endDate: String?,
    val read: Boolean,
    val status: String,
    val location: String
)