package com.example.smartlockerandroid.ui.components.profile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FriendRequestTile(
    name: String,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = onAccept) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Accept",
                tint = Color.Green
            )
        }

        IconButton(onClick = onDecline) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Decline",
                tint = Color.Red
            )
        }
    }
}
