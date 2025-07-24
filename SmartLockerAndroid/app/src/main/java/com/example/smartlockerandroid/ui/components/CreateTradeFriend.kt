package com.example.smartlockerandroid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.data.model.trade.input.CreateTradeRequest

@Composable
fun CreateTradeFriend(
    name: String,
    days: Int,
    onClick: (CreateTradeRequest) -> Unit = {},
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
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
                Text(text = stringResource(R.string.friends_for)+" "+days.toString(), fontWeight = FontWeight.Bold, color = Color.DarkGray)
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