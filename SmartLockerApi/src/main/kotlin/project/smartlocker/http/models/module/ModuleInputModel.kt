package project.smartlocker.http.models.module

data class CreateModuleRequest(
    val latitude: Double,
    val longitude: Double,
    val maxN: Int,
    val locName: String
)

data class UpdateModuleRequest(
    val latitude: Double,
    val longitude: Double,
    val maxN: Int,
    val locName: String,
    val status: String
)
