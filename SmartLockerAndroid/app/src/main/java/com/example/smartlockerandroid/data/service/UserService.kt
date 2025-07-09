package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.user.input.CreateUserRequest
import com.example.smartlockerandroid.data.model.user.input.UpdateUserRequest
import com.example.smartlockerandroid.data.model.user.input.UpdateUserStatusRequest
import com.example.smartlockerandroid.data.model.user.output.UserDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

interface UserService {
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDTO

    @GET("user/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): UserDTO

    @POST("user")
    suspend fun createUser(@Body input: CreateUserRequest)

    @PUT("user/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body input: UpdateUserRequest):UserDTO

    @PUT("user/{id}/status")
    suspend fun validateUser(@Path("id") id: Int, @Body input: UpdateUserStatusRequest)

}