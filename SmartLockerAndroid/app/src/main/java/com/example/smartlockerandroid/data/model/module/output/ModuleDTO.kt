package com.example.smartlockerandroid.data.model.module.output

import com.example.smartlockerandroid.data.model.module.Location

data class ModuleDTO(
    val id: Int,
    val location: Location,
    val locName: String,
    val maxN: Int,
    val status: String,
    val availableLockers: Int
)