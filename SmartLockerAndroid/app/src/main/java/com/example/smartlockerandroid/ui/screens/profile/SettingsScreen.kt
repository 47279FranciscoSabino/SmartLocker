package com.example.smartlockerandroid.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.TokenProvider
import com.example.smartlockerandroid.data.service.UserService
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    onBackRequest: () -> Unit,
    userService: UserService
) {
    val context = LocalContext.current
    val token = remember { mutableStateOf<String?>(null) }
    token.value = TokenProvider.getToken(context)



    val settingsViewModel: SettingsViewModel = viewModel(
        factory = viewModelInit { SettingsViewModel(userService, token.value) }
    )

    val user = settingsViewModel.user

    val email = remember { mutableStateOf("")}

    val newPassword = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val passwordMismatch = newPassword.value.isNotBlank() && newPassword.value != confirmPassword.value

    val loading = settingsViewModel.isLoading
    val error = settingsViewModel.errorMessage

    Scaffold(
        topBar = {
            TopBar(
                onBackRequest = onBackRequest
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            when {
                loading -> {
                    CircularProgressIndicator(
                        color = Color.Transparent
                    )
                }

                error != null -> {
                    Text("Error: $error")
                }

                else -> {
                    if(user != null){
                        Text("Edit Profile", style = MaterialTheme.typography.titleLarge)

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            label = { Text("Email") },
                            singleLine = true,
                            //keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = newPassword.value,
                            onValueChange = { newPassword.value = it },
                            label = { Text("New Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = confirmPassword.value,
                            onValueChange = { confirmPassword.value = it },
                            label = { Text("Confirm New Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (passwordMismatch) {
                            Text("Passwords do not match", color = Color.Red, style = MaterialTheme.typography.bodySmall)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (!passwordMismatch) {
                                    settingsViewModel.updateUser(
                                        token.value,
                                        email.value,
                                        newPassword.value,
                                    )
                                    onBackRequest()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save Changes")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                    }
                }
            }
        }
    }
}
