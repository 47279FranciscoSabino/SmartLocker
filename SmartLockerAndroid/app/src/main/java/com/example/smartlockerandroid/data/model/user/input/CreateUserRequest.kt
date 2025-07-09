package com.example.smartlockerandroid.data.model.user.input

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String
)