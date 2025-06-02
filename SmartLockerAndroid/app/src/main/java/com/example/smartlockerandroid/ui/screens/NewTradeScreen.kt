package com.example.smartlockerandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartlockerandroid.ui.components.NewTradeButton
import com.example.smartlockerandroid.ui.theme.MyBlue

@Composable
fun NewTradeScreen(
    onClickLogIn: () -> Unit = { }
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, // centers vertically
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Box(modifier = Modifier.background(MyBlue).size(350.dp)) {

        }
        Spacer(modifier = Modifier.height(80.dp))
        Box {
            NewTradeButton { onClickLogIn() }
        }
    }
}

@Preview(showBackground  = true)
@Composable
fun NewLockerPreview() {
    NewTradeScreen()
}