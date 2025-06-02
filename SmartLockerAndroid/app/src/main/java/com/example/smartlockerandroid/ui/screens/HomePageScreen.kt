package com.example.smartlockerandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.ui.components.LogInButton
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.ui.theme.BlueL

@Composable
fun HomePageScreen(
    onInfoRequest: (() -> Unit)? = null,
    onClickLogIn: () -> Unit = { }
){
    Scaffold(
        topBar = {
            TopBar(
                onInfoRequest = onInfoRequest
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center, // centers vertically
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Smart Locker",
                    fontStyle = FontStyle.Italic,
                    fontSize = 55.sp,
                    fontWeight = FontWeight.Bold,
                    color = BlueL
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
            Box {
                Image(
                    painter = painterResource(id = R.drawable.smartlocker_icon),
                    contentDescription = "Smart Locker Logo",
                    modifier = Modifier.size(180.dp)
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
            Box {
                LogInButton { onClickLogIn() }
            }
        }
    }
}

@Preview(showBackground  = true)
@Composable
fun HomePagePreview() {
    HomePageScreen()
}

