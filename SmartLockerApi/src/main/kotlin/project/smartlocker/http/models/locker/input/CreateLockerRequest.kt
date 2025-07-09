package project.smartlocker.http.models.locker.input

data class CreateLockerRequest(
    val module: Int,
    val hash: String,
    val active: Boolean
)