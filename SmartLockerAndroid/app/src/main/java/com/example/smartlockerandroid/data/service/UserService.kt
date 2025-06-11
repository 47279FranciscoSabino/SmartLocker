package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.trade.CreateTradeRequest
import com.example.smartlockerandroid.data.model.user.UpdateUserRequest
import com.example.smartlockerandroid.data.model.user.UserDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

interface UserService {
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDTO

    @POST("user")
    suspend fun createUser(@Body input: CreateTradeRequest)

    @PUT("user/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body input: UpdateUserRequest):UserDTO
}