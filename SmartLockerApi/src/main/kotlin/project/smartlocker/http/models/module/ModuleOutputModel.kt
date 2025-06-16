package project.smartlocker.http.models.module

import project.smartlocker.domain.module.Location

data class ModuleDTO(
    val id: Int,
    val location: Location,
    val maxN: Int,
    val locName: String,
    val status: String
)

data class ModuleAppDTO(
    val id: Int,
    val location: Location,
    val maxN: Int,
    val locName: String,
    val status: String,
    val availableLockers: Int
)