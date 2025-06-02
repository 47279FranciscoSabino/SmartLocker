package project.smartlocker.domain.locker

data class LockerStatus(
    val locker: Int,
    val status: LockerEnum
)

enum class LockerEnum {
    IN_USE, AVAILABLE, MAINTENANCE
}
