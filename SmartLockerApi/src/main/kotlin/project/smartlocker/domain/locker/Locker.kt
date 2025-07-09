package project.smartlocker.domain.locker

data class Locker(
    val id: Int,
    val module: Int,
    val hash: String,
    val active: Boolean
)