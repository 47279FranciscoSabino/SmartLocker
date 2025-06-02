package com.example.smartlockerandroid.data.model.friends


data class CreateFriendRequest(
    val user: Int,
    val friend: Int,
    val date: String
)

data class UpdateFriendRequest(
    val user: Int,
    val friend: Int,
    val date: String,
    val status: String
)