package com.example.smartlockerandroid.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.smartlockerandroid.data.model.locker.LockerDTO
import com.example.smartlockerandroid.data.service.LockerService

@Composable
fun LockerLoader(
    lockerId: Int,
    service: LockerService,
    content: @Composable (LockerDTO?) -> Unit
) {
    var locker by remember { mutableStateOf<LockerDTO?>(null) }
    var hasTried by remember { mutableStateOf(false) }

    LaunchedEffect(lockerId) {
        if (!hasTried) {
            try {
                locker = service.getLockerById(lockerId)
            } catch (e: Exception) {
                // You can log or handle errors here
            } finally {
                hasTried = true
            }
        }
    }

    content(locker)
}