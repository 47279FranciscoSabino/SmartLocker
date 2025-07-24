package com.example.smartlockerandroid.ui.components.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.data.model.user.output.UserDTO
import com.example.smartlockerandroid.viewmodel.ProfileViewModel

@Composable
fun SearchFriendDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onSendRequest: (UserDTO) -> Unit,
    viewModel: ProfileViewModel
) {
    if (!show) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.search_for_user)) },
        text = {
            Column {
                OutlinedTextField(
                    value = viewModel.searchQuery,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    label = { Text(stringResource(R.string.username)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                when {
                    viewModel.isLoading -> {
                        CircularProgressIndicator()
                    }

                    viewModel.searchedUser != null -> {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSendRequest(viewModel.searchedUser!!)
                                    onDismiss()
                                }
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = viewModel.searchedUser!!.username,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    viewModel.searchQuery.isNotBlank() -> {
                        Text(
                            text = viewModel.errorMessage ?: "User not found",
                            color = Color.Red,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.close))
            }
        }
    )
}