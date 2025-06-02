package project.smartlocker.http.models.locker

data class CreateLockerRequest(
    val module: Int,
    val qr: String,
    val active: Boolean
)

data class UpdateLockerRequest(
    val module: Int,
    val qr: String,
    val active: Boolean,
    val status: String
)
