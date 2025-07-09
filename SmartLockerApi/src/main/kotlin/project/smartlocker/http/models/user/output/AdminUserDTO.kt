package project.smartlocker.http.models.user.output

data class AdminUserDTO(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val status: String
)