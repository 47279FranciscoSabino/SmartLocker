package com.example.smartlockerandroid.data.model.locker

data class LockerDTO(
    val id: Int,
    val module: Int,
    val qr: String,
    val active: Boolean,
    val status: String
)