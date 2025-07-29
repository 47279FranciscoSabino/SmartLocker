package com.example.smartlockerandroid

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.edit
import androidx.navigation.compose.rememberNavController
import androidx.preference.PreferenceManager
import com.example.smartlockerandroid.ui.theme.SmartLockerAndroidTheme
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate called")
        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prefs = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

        setContent {
            SmartLockerAndroidTheme(
                darkTheme = false
            ) {
                val navController = rememberNavController()
                Nav(navController = navController)
            }
        }
    }
}

object TokenProvider {
    fun getToken(context: Context): String? {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return prefs.getString("auth_token", null)
    }

    fun saveToken(context: Context, token: String) {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("auth_token", token).commit()
    }

    fun clearToken(context: Context) {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit { remove("auth_token") }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainActivity()
}