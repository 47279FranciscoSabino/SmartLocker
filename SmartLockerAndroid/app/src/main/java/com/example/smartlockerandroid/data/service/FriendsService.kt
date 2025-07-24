package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.friends.input.CreateFriendRequest
import com.example.smartlockerandroid.data.model.friends.input.UpdateFriendRequest
import com.example.smartlockerandroid.data.model.friends.output.FriendDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FriendsService {
    @GET("friends")
    suspend fun getUserFriends(@Header("Authorization") token: String): List<FriendDTO>

    @POST("friend")
    suspend fun addFriend(@Body input: CreateFriendRequest, @Header("Authorization") token: String)

    @PUT("friend/{friendId}")
    suspend fun editFriend(@Path("friendId") friendId:Int, @Body input: UpdateFriendRequest, @Header("Authorization") token: String)

    @DELETE("friend/{friendId}")
    suspend fun removeFriend(@Path("friendId") friendId:Int, @Header("Authorization") token: String)
}