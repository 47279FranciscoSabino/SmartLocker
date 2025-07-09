package project.smartlocker.http.models.user.input

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String
)