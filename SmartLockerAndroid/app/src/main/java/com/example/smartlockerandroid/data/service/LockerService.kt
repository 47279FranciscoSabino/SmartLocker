package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.locker.LockerDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface LockerService {
    @GET("lockers")
    suspend fun getAllLockers(): List<LockerDTO>

    @GET("locker/{id}")
    suspend fun getLockerById(@Path("id") id: Int): LockerDTO
}