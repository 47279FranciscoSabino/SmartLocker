package project.smartlocker.http.models.locker.output

data class LockerDTO(
    val id: Int,
    val module: Int,
    val hash: String,
    val active: Boolean,
    val status: String
)