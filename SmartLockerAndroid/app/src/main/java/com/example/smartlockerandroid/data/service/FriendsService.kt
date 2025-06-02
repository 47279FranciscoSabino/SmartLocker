package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.friends.CreateFriendRequest
import com.example.smartlockerandroid.data.model.friends.FriendDTO
import com.example.smartlockerandroid.data.model.friends.UpdateFriendRequest
import com.example.smartlockerandroid.data.model.trade.CreateTradeRequest
import com.example.smartlockerandroid.data.model.user.UpdateUserRequest
import com.example.smartlockerandroid.data.model.user.UserDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FriendsService {
    @GET("/user/{id}/friends")
    suspend fun getAllFriends(@Path("id") id: Int): List<FriendDTO>

    @GET("/user/{id}/friend/{friendId}")
    suspend fun getFriend(@Path("id") id: Int, @Path("friendId") friendId:Int): FriendDTO

    @POST("/user/{id}/friend")
    suspend fun createFriends(@Path("id") id: Int, @Body input: CreateFriendRequest)

    @PUT("/user/{id}/friend/{friendId}")
    suspend fun updateFriends(@Path("id") id:Int, @Path("friendId") friendId:Int, @Body input: UpdateFriendRequest): FriendDTO

    @DELETE("/user/{id}/friend/{friendId}")
    suspend fun deleteFriends(@Path("id") id:Int, @Path("friendId") friendId:Int)
}