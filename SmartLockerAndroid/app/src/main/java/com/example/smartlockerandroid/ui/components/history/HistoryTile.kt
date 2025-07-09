package com.example.smartlockerandroid.ui.components.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlockerandroid.ui.theme.MyBlue

@Composable
fun HistoryTile(location: String, date :String, onClick: () -> Unit) {
    Box(modifier = Modifier.background(MyBlue)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f).height(50.dp), verticalArrangement = Arrangement.Center) {
                Text(text = location, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = Color.White)
                Text(text = date, fontWeight = FontWeight(10), color = Color.LightGray )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
    Spacer(modifier = Modifier.height(1.dp))
}

@Preview
@Composable
private fun ReceiverTilePreview() {
    HistoryTile(
        location = "chelas",
        date = "10-02-2022",
        onClick = { }
    )
}