package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.user.input.CreateUserRequest
import com.example.smartlockerandroid.data.model.user.input.LoginRequest
import com.example.smartlockerandroid.data.service.UserService
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userService: UserService
) : ViewModel() {

    var authToken by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isSuccess by mutableStateOf(false)
        private set

    fun onToken(token: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val bearerToken = "Bearer ${token}"
                userService.getProfile(bearerToken)
                isSuccess = true
            } catch (e: Exception) {
                errorMessage = "Login failed: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    fun onLogin(input: LoginRequest) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = userService.login(input)
                authToken = response.token.toString()
                isSuccess = true
            } catch (e: Exception) {
                errorMessage = "Login failed: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    fun onSignup(input: CreateUserRequest) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                userService.register(input)
                errorMessage = "Account created. Please verify your email before logging in."
            } catch (e: Exception) {
                errorMessage = "Signup failed: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    fun resetStatus() {
        isSuccess = false
        authToken = null
        errorMessage = null
    }
}
