package com.example.smartlockerandroid.data.model.user

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String
)

data class UpdateUserRequest(
    val username: String,
    val email: String,
    val password: String,
    val status: String
)