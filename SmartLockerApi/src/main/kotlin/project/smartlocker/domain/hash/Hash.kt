package project.smartlocker.domain.hash

data class Hash(
    val hash: String,
    val locker: Int,
    val qr: String
)