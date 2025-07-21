package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.hash.output.QrScanDTO
import com.example.smartlockerandroid.data.model.locker.output.LockerDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ScanService {
    @GET("hash/{hash}")
    suspend fun getHash(@Path("hash") hash: String?, @Header("Authorization") token: String): QrScanDTO

    @GET("scan/{hash}")
    suspend fun scan(@Path("hash") hash: String?, @Header("Authorization") token: String): LockerDTO

}