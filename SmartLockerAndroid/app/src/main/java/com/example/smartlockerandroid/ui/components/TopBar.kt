package com.example.smartlockerandroid.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartlockerandroid.ui.theme.SmartLockerAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    text:String? = null,
    onBackRequest: (() -> Unit)? = null,
    onHomeRequest: (() -> Unit)? = null,
    onProfileRequest: (() -> Unit)? = null
) {
    SmartLockerAndroidTheme(){
        TopAppBar(
            title =  {
                if (text != null){
                    Text(
                        text = text,
                        textAlign = TextAlign.Center,
                    )
                }else{
                    Text(
                        text = "",
                    ) }
            },
            navigationIcon = {
                if (onBackRequest != null) {
                    IconButton(onClick = onBackRequest) {
                        Icon(Icons.Default.ArrowBack, tint = Color.Black, contentDescription = null)
                    }
                }
            },
            actions = {
                if (onHomeRequest != null) {
                    IconButton(onClick = onHomeRequest) {
                        Icon(Icons.Default.Home, tint = Color.Gray, contentDescription = null)
                    }
                }
                if (onProfileRequest != null) {
                    IconButton(onClick = onProfileRequest) {
                        Icon(Icons.Default.AccountCircle, tint = Color.Black, contentDescription = null)
                    }
                }
            })
    }

}

@Preview
@Composable
private fun TopBarPreviewBack() {
    TopBar(text= "pagina", onBackRequest = { })
}

@Preview
@Composable
private fun TopBarPreviewBackAndLogin() {
    TopBar(
        onBackRequest = { }
    )
}

@Preview
@Composable
private fun TopBarPreviewProfile() {
    TopBar(
        onProfileRequest = { }
    )
}

@Preview
@Composable
private fun TopBarPreviewAll() {
    TopBar(
        onBackRequest = { },
        onHomeRequest = { },
        onProfileRequest = { }
    )
}