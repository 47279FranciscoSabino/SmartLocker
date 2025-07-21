package project.smartlocker.http.models.user.output

data class UserDTO(
    val id: Int,
    val username: String,
    val email: String,
    val status: String,
    val role: String
)