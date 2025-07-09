package com.example.smartlockerandroid.data.model.friends.output

data class FriendDTO(
    val user: Int,
    val name: String,
    val date: String?,
    val days: Int,
    val status: String
)