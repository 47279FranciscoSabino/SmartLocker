package project.smartlocker.http.models.user.input

data class LoginRequest(
    val email: String,
    val password: String
)