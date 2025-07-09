package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.user.input.UpdateUserRequest
import com.example.smartlockerandroid.data.model.user.output.UserDTO
import com.example.smartlockerandroid.data.service.UserService
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userService: UserService,
    private val userId: Int
) : ViewModel() {

    var user by mutableStateOf<UserDTO?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                user = userService.getUserById(userId)

            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to load user"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateUser(
        email: String?,
        password: String?
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                if(user!=null){
                    val request = UpdateUserRequest(
                        email = email,
                        password = password
                    )

                    userService.updateUser(userId, request)
                    loadUser()
                }

            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to update user"
            } finally {
                isLoading = false
            }
        }
    }
}