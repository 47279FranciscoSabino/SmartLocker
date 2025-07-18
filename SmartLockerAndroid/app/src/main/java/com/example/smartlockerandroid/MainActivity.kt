package com.example.smartlockerandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.preference.PreferenceManager
import com.example.smartlockerandroid.ui.theme.SmartLockerAndroidTheme
import org.osmdroid.config.Configuration


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))

        super.onCreate(
            savedInstanceState
        )
        enableEdgeToEdge()
        setContent {
            SmartLockerAndroidTheme {
                val navController = rememberNavController()
                Nav(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainActivity()
}