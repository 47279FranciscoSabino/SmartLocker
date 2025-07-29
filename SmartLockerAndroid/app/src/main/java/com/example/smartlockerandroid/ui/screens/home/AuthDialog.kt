package com.example.smartlockerandroid.ui.screens.home

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.TokenProvider
import com.example.smartlockerandroid.data.model.user.input.CreateUserRequest
import com.example.smartlockerandroid.data.model.user.input.LoginRequest
import com.example.smartlockerandroid.data.service.UserService
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    userService: UserService
) {
    val viewModel: AuthViewModel = viewModel(
        factory = viewModelInit { AuthViewModel(userService) }
    )
    val context = LocalContext.current

    val isLogin = remember { mutableStateOf(true) }
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage
    val token = viewModel.authToken
    val savedToken = TokenProvider.getToken(context)

    LaunchedEffect(Unit, savedToken) {
        Log.d("AuthScreen", "Saved token: $savedToken")
        if (!savedToken.isNullOrBlank()) {
            viewModel.onToken(savedToken)
            //Log.d("AuthScreen", "Token found, navigating...")
            //onClick()
        } else {
            Log.d("AuthScreen", "No token found")
        }
    }

    LaunchedEffect(viewModel.isSuccess, token, savedToken) {
        if (viewModel.isSuccess && token != null) {
            Log.d("AuthScreen", "Saving token: $token")
            TokenProvider.saveToken(context, token)
            onClick()
            viewModel.resetStatus()
        }
        else if (viewModel.isSuccess && savedToken != null) {
            Log.d("AuthScreen", "Saving token: $token")
            onClick()
            viewModel.resetStatus()
        }
    }

    // UI remains the same
    Column(
        modifier = Modifier
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text("Error: $error", color = Color.Red)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.resetStatus() }) {
                    Text("Try Again")
                }
            }
            else -> {
                Text(
                    text = if (isLogin.value) stringResource(R.string.login) else stringResource(R.string.signup),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (!isLogin.value) {
                    OutlinedTextField(
                        value = username.value,
                        onValueChange = { username.value = it },
                        label = { Text(stringResource(R.string.username)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(stringResource(R.string.email)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text( stringResource(R.string.password)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (isLogin.value) {
                            viewModel.onLogin(LoginRequest(email.value, password.value))
                        } else {
                            viewModel.onSignup(CreateUserRequest(username.value, email.value, password.value))
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isLogin.value) stringResource(R.string.login) else stringResource(R.string.signup))
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { isLogin.value = !isLogin.value }) {
                    Text(if (isLogin.value) stringResource(R.string.swap_signup) else stringResource(R.string.swap_login))
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}