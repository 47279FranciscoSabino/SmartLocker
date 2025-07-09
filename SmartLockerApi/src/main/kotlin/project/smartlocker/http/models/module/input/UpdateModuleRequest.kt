package project.smartlocker.http.models.module.input

data class UpdateModuleRequest(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val locName: String? = null,
    val maxN: Int? = null,
    val status: String? = null
)