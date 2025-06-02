package project.smartlocker.http.models.user

data class UserDTO(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val status: String
)

