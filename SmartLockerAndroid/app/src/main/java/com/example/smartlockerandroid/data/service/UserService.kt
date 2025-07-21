package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.user.input.CreateUserRequest
import com.example.smartlockerandroid.data.model.user.input.UpdateUserRequest
import com.example.smartlockerandroid.data.model.user.input.UpdateUserStatusRequest
import com.example.smartlockerandroid.data.model.user.output.UserDTO
import com.example.smartlockerandroid.data.model.user.input.LoginRequest
import com.example.smartlockerandroid.data.model.user.output.TokenDTO
import com.example.smartlockerandroid.data.model.user.output.UserInfoDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

interface UserService {
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDTO

    @GET("user/{username}")
    suspend fun getUserByUsername(@Path("username") username: String, @Header("Authorization") token: String): UserDTO

    @POST("user")
    suspend fun createUser(@Body input: CreateUserRequest)

    @PUT("user/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body input: UpdateUserRequest):UserDTO

    @PUT("user/{id}/status")
    suspend fun validateUser(@Path("id") id: Int, @Body input: UpdateUserStatusRequest)



    @POST("login")
    suspend fun login(@Body input: LoginRequest): TokenDTO

    @POST("logout")
    suspend fun logout(@Header("Authorization") token: String)

    @POST("signup")
    suspend fun register(@Body input: CreateUserRequest)

    @GET("profile")
    suspend fun getProfile(@Header("Authorization") token: String):UserDTO

    @PUT("profile")
    suspend fun updateProfile(@Body input: UpdateUserRequest, @Header("Authorization") token: String)

    @GET("users/{id}")
    suspend fun getUserInfo(@Path("id") id: Int, @Header("Authorization") token: String): UserInfoDTO

}