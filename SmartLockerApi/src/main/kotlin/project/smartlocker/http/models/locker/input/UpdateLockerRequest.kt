package project.smartlocker.http.models.locker.input

data class UpdateLockerRequest(
    val module: Int? = null,
    val hash: String? = null,
    val active: Boolean? = null,
    val status: String? = null
)