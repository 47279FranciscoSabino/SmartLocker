package project.smartlocker.domain.user

data class UserStatus(
    val user: Int,
    val status: UserEnum
)

enum class UserEnum {
    VERIFIED, NOT_VERIFIED, SUSPENDED
}
