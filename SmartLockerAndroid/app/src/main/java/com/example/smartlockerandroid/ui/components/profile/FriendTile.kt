package com.example.smartlockerandroid.ui.components.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlockerandroid.R


@Composable
fun FriendTile(
    name: String,
    days: Int,
    //onClick: () -> Unit = {},
    onBlock: () -> Unit = {},
    onUnfriend: () -> Unit = {}
) {
    val expanded = remember { mutableStateOf(false) }
    val showBlockDialog = remember { mutableStateOf(false) }
    val showUnfriendDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .clickable {
            expanded.value = !expanded.value
            //onClick()
        }
        .padding(horizontal = 16.dp, vertical = 12.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = name, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                Text(text = stringResource(R.string.friends_for)+days.toString(), fontWeight = FontWeight.Bold, color = Color.DarkGray)
            }
            Icon(
                imageVector = if (expanded.value) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }

        // Expanded content
        AnimatedVisibility(visible = expanded.value) {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

                Text(
                    text = stringResource(R.string.block),
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showBlockDialog.value = true }
                        .padding(vertical = 8.dp)
                )

                Text(
                    text = stringResource(R.string.unfriend),
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showUnfriendDialog.value = true }
                        .padding(vertical = 8.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(1.dp))

    // Block confirmation dialog
    if (showBlockDialog.value) {
        AlertDialog(
            onDismissRequest = { showBlockDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    showBlockDialog.value = false
                    onBlock()
                }) {
                    Text(stringResource(R.string.block), color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showBlockDialog.value = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            title = { Text("Confirm Block") },
            text = { Text("Are you sure you want to block $name?") }
        )
    }

    // Unfriend confirmation dialog
    if (showUnfriendDialog.value) {
        AlertDialog(
            onDismissRequest = { showUnfriendDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    showUnfriendDialog.value = false
                    onUnfriend()
                }) {
                    Text(stringResource(R.string.unfriend), color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showUnfriendDialog.value = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            title = { Text("Confirm Unfriend") },
            text = { Text("Are you sure you want to unfriend $name?") }
        )
    }
}

@Preview
@Composable
private fun FriendTilePreview() {
    FriendTile(
        name = "chelas",
        days = 50,
        //onClick = { }
    )
}