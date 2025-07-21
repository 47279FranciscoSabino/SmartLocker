package project.smartlocker.domain.user

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val token: String?
)

enum class RoleEnum {
    STANDARD, ADMIN
}