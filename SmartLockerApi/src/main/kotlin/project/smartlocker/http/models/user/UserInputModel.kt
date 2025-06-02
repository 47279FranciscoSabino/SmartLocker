package project.smartlocker.http.models.user

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String
)

data class UpdateUserRequest(
    val username: String,
    val email: String,
    val password: String,
    val status: String
)