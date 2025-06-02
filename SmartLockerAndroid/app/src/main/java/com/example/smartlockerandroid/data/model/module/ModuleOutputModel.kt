package com.example.smartlockerandroid.data.model.module

data class ModuleDTO(
    val id: Int,
    val location: Location,
    val maxN: Int,
    val locName: String,
    val status: String
)
