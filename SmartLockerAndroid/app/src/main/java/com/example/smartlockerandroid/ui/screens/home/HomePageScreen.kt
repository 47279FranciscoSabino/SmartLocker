package com.example.smartlockerandroid.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.data.service.UserService
import com.example.smartlockerandroid.ui.components.LogInButton
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.ui.theme.MyBlue2
@Composable
fun HomePageScreen(
    onInfoRequest: (() -> Unit)? = null,
    onClickLogIn: () -> Unit = {},
    userService: UserService
) {
    val showAuthDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Smart Locker",
            fontStyle = FontStyle.Italic,
            fontSize = 55.sp,
            fontWeight = FontWeight.Bold,
            color = MyBlue2
        )

        Spacer(modifier = Modifier.height(80.dp))

        Image(
            painter = painterResource(id = R.drawable.smartlocker_icon),
            contentDescription = "Smart Locker Logo",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(80.dp))

        LogInButton {
            showAuthDialog.value = true
        }

        // Login/sign up
        if (showAuthDialog.value) {
            Dialog(onDismissRequest = { showAuthDialog.value = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.background,
                    tonalElevation = 8.dp
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        AuthScreen(
                            onDismiss = { showAuthDialog.value = false },
                            onClick = onClickLogIn,
                            userService = userService
                        )
                    }
                }
            }
        }
    }
}


