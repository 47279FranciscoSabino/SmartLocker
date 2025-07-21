package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlockerandroid.data.model.friends.output.FriendDTO
import com.example.smartlockerandroid.data.model.friends.input.UpdateFriendRequest
import com.example.smartlockerandroid.data.model.user.output.UserDTO
import com.example.smartlockerandroid.data.service.FriendsService
import com.example.smartlockerandroid.data.service.UserService
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userService: UserService,
    private val friendsService: FriendsService,
    private val token: String?
) : ViewModel() {

    var user by mutableStateOf<UserDTO?>(null)
        private set

    var friends by mutableStateOf<List<FriendDTO>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadProfile()
    }

    fun loadProfile(){
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                user = userService.getProfile(bearerToken)
                friends = friendsService.getUserFriends(bearerToken)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Error on profile request"
            } finally {
                isLoading = false
            }
        }
    }

    var searchedUser by mutableStateOf<UserDTO?>(null)
        private set

    var searchQuery by mutableStateOf("")
        private set

    fun onSearchQueryChanged(query: String) {1
        searchQuery = query
        searchUserByUsername(query)
    }

    private fun searchUserByUsername(username: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                searchedUser = userService.getUserByUsername(username, bearerToken)
            } catch (e: Exception) {
                searchedUser = null
                errorMessage = e.message ?: "User not found"
            } finally {
                isLoading = false
            }
        }
    }

    fun sendFriendRequest(friend: UserDTO) {
        viewModelScope.launch {
            try{
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                friendsService.addFriend(friend.id, bearerToken)
            } catch (e: Exception){
                errorMessage = e.message?: "Error: user not found"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateFriendRequest(friend: Int, input: UpdateFriendRequest){
        viewModelScope.launch {
            try{
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                friendsService.editFriend(friend, input, bearerToken)
                loadProfile()
            } catch (e:Exception){
                errorMessage = e.message?:"Error updating friend"
            } finally {
                isLoading = false
            }
        }
    }

    fun removeFriendRequest(friend: Int){
        viewModelScope.launch {
            try{
                isLoading = true
                errorMessage = null

                val bearerToken = "Bearer ${token}"
                friendsService.removeFriend(friend, bearerToken)
                loadProfile()
            } catch (e:Exception){
                errorMessage = e.message?:"Error updating friend"
            } finally {
                isLoading = false
            }
        }
    }
}