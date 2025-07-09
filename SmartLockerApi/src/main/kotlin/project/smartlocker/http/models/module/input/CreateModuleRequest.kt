package project.smartlocker.http.models.module.input

data class CreateModuleRequest(
    val longitude: Double,
    val latitude: Double,
    val locName: String,
    val maxN: Int
)