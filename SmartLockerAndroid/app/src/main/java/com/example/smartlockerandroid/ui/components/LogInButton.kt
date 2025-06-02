package com.example.smartlockerandroid.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlockerandroid.ui.theme.MyBlue


@Composable
fun LogInButton(onClick: () -> Unit){
    Button(
        onClick = onClick,
        colors = ButtonColors(MyBlue, Color.White, MyBlue, Color.White),
        modifier = Modifier
            .size(height = 130.dp, width = 320.dp)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Login/Signup",

            fontSize = 18.sp,
        )
    }
}

@Preview
@Composable
fun LogInButtonPreview() {
    LogInButton(
        onClick = { /* Do nothing for preview */ },
    )
}