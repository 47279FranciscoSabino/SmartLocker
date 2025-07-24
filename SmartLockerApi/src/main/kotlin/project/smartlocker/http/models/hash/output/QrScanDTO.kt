package com.example.smartlockerandroid.data.model.hash.output

import project.smartlocker.http.models.user.output.UserDTO

data class QrScanDTO(
    val hash: String,
    val locker: Int,
    val qr: ByteArray
)