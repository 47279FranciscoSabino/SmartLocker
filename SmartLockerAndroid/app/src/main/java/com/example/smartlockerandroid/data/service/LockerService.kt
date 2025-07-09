package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.locker.output.LockerDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface LockerService {
    @GET("locker/{id}")
    suspend fun getLockerById(@Path("id") id: Int): LockerDTO

    @GET("locker/hash/{hash}")
    suspend fun getLockerByHash(@Path("hash") hash: String): LockerDTO
}