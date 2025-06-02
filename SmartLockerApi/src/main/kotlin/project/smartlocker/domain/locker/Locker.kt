package project.smartlocker.domain.locker

data class Locker(
    val id: Int,
    val module: Int,
    val qr: String,
    val active: Boolean
)