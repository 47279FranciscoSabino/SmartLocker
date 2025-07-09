package project.smartlocker.http.models.module.output

import project.smartlocker.domain.module.Location

data class ModuleDTO(
    val id: Int,
    val location: Location,
    val locName: String,
    val maxN: Int,
    val status: String,
    val availableLockers: Int
)