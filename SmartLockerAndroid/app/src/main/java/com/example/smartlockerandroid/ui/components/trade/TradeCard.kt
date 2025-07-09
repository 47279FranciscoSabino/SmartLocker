package com.example.smartlockerandroid.ui.components.trade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlockerandroid.data.model.trade.output.TradeDTO

@Composable
fun TradeCard(trade: TradeDTO, location:String, sender:String, receiver:String){
    Box{
        Text(
            text = trade.status,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp
        )
    }
    Spacer(modifier = Modifier.height(150.dp))
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.White)
            .padding(40.dp)
            .size(250.dp)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = location,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                fontSize = 28.sp
            )
            Text(
                text = ("From: $sender"),
                color = Color.Gray,
                fontSize = 22.sp
            )
            Text(
                text = ("To: $receiver"),
                color = Color.Gray,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = ("Started: ${trade.startDate}"),
                color = Color.Gray,
                fontSize = 20.sp
            )
            if(trade.status!= "PENDING"){
                Text(
                    text = ("Ended: ${trade.endDate}"),
                    color = Color.Gray,
                    fontSize = 20.sp
                )
            }
        }
    }

}

@Preview
@Composable
fun TradeCardPreview(){
    TradeCard( TradeDTO(1,1, 2, 3, "02-05-2020", "03-09-2022", false, "COMPLETED"), "coina", "Andre", "Miguel")
}