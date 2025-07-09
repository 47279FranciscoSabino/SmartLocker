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
    private val userId: Int
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

                user = userService.getUserById(userId)
                friends = friendsService.getAllFriends(userId)

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

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        searchUserByUsername(query)
    }

    private fun searchUserByUsername(username: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                searchedUser = userService.getUserByUsername(username)
            } catch (e: Exception) {
                searchedUser = null
                errorMessage = e.message ?: "User not found"
            } finally {
                isLoading = false
            }
        }
    }

    fun sendFriendRequest(myId: Int, username: String) {
        viewModelScope.launch {
            try{
                isLoading = true
                errorMessage = null

                val friend = userService.getUserByUsername(username)
                friendsService.addFriend(myId, friend.id)
            } catch (e: Exception){
                errorMessage = e.message?: "Error: user not found"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateFriendRequest(myId: Int, friend: Int, input: UpdateFriendRequest){
        viewModelScope.launch {
            try{
                isLoading = true
                errorMessage = null

                friendsService.updateFriends(myId, friend, input)
                loadProfile()
            } catch (e:Exception){
                errorMessage = e.message?:"Error updating friend"
            } finally {
                isLoading = false
            }
        }
    }

    fun removeFriendRequest(myId: Int, friend: Int){
        viewModelScope.launch {
            try{
                isLoading = true
                errorMessage = null

                friendsService.deleteFriends(myId, friend)
                loadProfile()
            } catch (e:Exception){
                errorMessage = e.message?:"Error updating friend"
            } finally {
                isLoading = false
            }
        }
    }
}