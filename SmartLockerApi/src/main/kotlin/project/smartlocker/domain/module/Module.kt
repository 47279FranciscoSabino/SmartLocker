package project.smartlocker.domain.module

data class Module(
    val id: Int,
    val location: Location,
    val locName: String,
    val maxN: Int
)