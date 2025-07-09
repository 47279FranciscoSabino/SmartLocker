package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.hash.output.QrScanDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ScanService {
    @GET("hash/{hash}")
    suspend fun getHash(@Path("hash") hash: String?): QrScanDTO
}