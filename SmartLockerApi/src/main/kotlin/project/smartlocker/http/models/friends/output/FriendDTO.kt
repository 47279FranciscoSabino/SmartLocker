package project.smartlocker.http.models.friends.output

data class FriendDTO(
    val user: Int,
    val name: String,
    val date: String?,
    val days: Int,
    val status: String
)