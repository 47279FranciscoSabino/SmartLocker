package com.example.smartlockerandroid.ui.components.history

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.ui.theme.MyBlue

@Composable
fun HistoryButton(onClick: () -> Unit){
    Button(
        onClick = onClick,
        colors = ButtonColors(MyBlue, Color.White, MyBlue, Color.White),
        modifier = Modifier
            .size(height = 80.dp, width = 250.dp)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.history_button),
            fontSize = 15.sp,
        )
    }
}

@Preview
@Composable
fun HistoryButtonPreview() {
    HistoryButton(
        onClick = { },
    )
}