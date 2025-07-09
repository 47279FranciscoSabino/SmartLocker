package project.smartlocker.http.models.user.input

data class UpdateUserRequest(
    val email: String? = null,
    val password: String? = null
)