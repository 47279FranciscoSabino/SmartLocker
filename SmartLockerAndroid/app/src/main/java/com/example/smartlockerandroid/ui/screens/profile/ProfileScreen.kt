package com.example.smartlockerandroid.ui.screens.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.data.model.friends.input.UpdateFriendRequest
import com.example.smartlockerandroid.data.model.user.output.UserDTO
import com.example.smartlockerandroid.data.service.FriendsService
import com.example.smartlockerandroid.data.service.UserService
import com.example.smartlockerandroid.ui.components.profile.FriendRequestTile
import com.example.smartlockerandroid.ui.components.profile.FriendTile
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.ui.components.profile.AddFriendButton
import com.example.smartlockerandroid.ui.components.profile.SearchFriendDialog
import com.example.smartlockerandroid.ui.theme.MyBlue
import com.example.smartlockerandroid.ui.theme.MyBlue3
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onInfoRequest: (() -> Unit)? = null,
    onBackRequest: (() -> Unit)? = null,
    onSettingsClick: ((UserDTO) -> Unit) = {},
    onLogoutClick: (() -> Unit) = {},
    userService: UserService,
    friendsService: FriendsService,
    userId: Int = 1

) {
    val profileViewModel: ProfileViewModel = viewModel(
        factory = viewModelInit { ProfileViewModel(userService, friendsService, userId) }
    )

    val showSearch = remember { mutableStateOf(false) }
    val showPending = remember { mutableStateOf(false) }

    val user = profileViewModel.user
    val friends = profileViewModel.friends
    val pending = friends.filter { it.status == "PENDING" }

    val loading = profileViewModel.isLoading
    val error = profileViewModel.errorMessage

    Scaffold(
        topBar = {
            TopBar(
                onInfoRequest = onInfoRequest,
                onBackRequest = onBackRequest
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            when {
                loading -> {
                    CircularProgressIndicator(
                        color = Color.Transparent
                    )
                }
                error != null -> {
                    Text("Error: $error")
                }
                else -> {
                    // User name
                    Text(text = user?.username.toString(), fontSize = 30.sp, fontWeight = FontWeight.Medium)

                    // Image
                    Box(Modifier.padding(10.dp)) {
                        Box(Modifier
                            .clip(shape = CircleShape)
                            .background(MyBlue3)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.default_user_icon),
                                contentDescription = "User Icon",
                                modifier = Modifier.size(180.dp)
                            )
                        }
                    }
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

                    // Popup Buttons
                    Row {
                        if(pending.isNotEmpty()){
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable(enabled = pending.isNotEmpty()) {
                                        showPending.value = true
                                    }
                                    .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            ) {
                                Text("Requests (${pending.size})", fontWeight = FontWeight.Bold)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ){
                            AddFriendButton(onClick = { showSearch.value = true })
                        }
                    }


                    if (showPending.value) {
                        Box (
                            Modifier
                                .fillMaxSize()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    showPending.value = false
                                }
                        ) {
                            Box(
                                Modifier
                                    .align(Alignment.Center)
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .padding(16.dp)
                                    .width(300.dp)
                                    .heightIn(max = 250.dp)
                            ) {
                                LazyColumn {
                                    item {
                                        Text("Pending Requests", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                        Spacer(Modifier.height(8.dp))
                                    }

                                    items(pending) { friend ->
                                        FriendRequestTile(
                                            name = friend.name,
                                            onAccept = {
                                                if (user != null) {
                                                    profileViewModel.updateFriendRequest(user.id, friend.user, UpdateFriendRequest("ACCEPTED"))
                                                }
                                                showPending.value = false
                                            },
                                            onDecline = {
                                                if (user != null) {
                                                    profileViewModel.removeFriendRequest(user.id, friend.user)
                                                }
                                                showPending.value = false
                                            }
                                        )
                                        Spacer(Modifier.height(8.dp))
                                    }

                                    item {
                                        TextButton(
                                            onClick = { showPending.value = false }
                                        ) {
                                            Text("Close")
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // List of Friends
                    Box(Modifier.weight(3f)){
                        if (friends.any { it.status == "ACCEPTED" }) {
                            Column(Modifier.padding(10.dp)) {
                                friends.forEach { friend ->
                                    if (friend.status == "ACCEPTED") {
                                        Box(modifier = Modifier.background(MyBlue)) {
                                            FriendTile(
                                                name = friend.name,
                                                days = friend.days,
                                                onBlock = {
                                                    if (user != null) {
                                                        profileViewModel.updateFriendRequest(user.id, friend.user, UpdateFriendRequest("BLOCKED"))
                                                    }
                                                },
                                                onUnfriend = {
                                                    if (user != null) {
                                                        profileViewModel.removeFriendRequest(user.id, friend.user)
                                                    }
                                                }
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(5.dp))
                                    }
                                }
                            }
                        } else {
                            Text("You dont have any friends );")
                        }
                    }

                    TextButton(onClick = {
                        if (user != null) {
                            onSettingsClick(user)
                        }
                    }) {
                        Text(text = "settings", color = Color.Gray)
                    }
                    TextButton(onClick = onLogoutClick) {
                        Text(text = "log out", color = Color.Red)
                    }
                }
            }
        }
        SearchFriendDialog(
            show = showSearch.value,
            onDismiss = { showSearch.value = false },
            onSendRequest = { username ->
                profileViewModel.sendFriendRequest(userId, username)
            },
            viewModel = profileViewModel
        )

    }
}