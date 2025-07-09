package project.smartlocker.http.models.friends.output

data class AdminFriendDTO(
    val user: Int,
    val friend: Int,
    val date: String?,
    val status: String
)
