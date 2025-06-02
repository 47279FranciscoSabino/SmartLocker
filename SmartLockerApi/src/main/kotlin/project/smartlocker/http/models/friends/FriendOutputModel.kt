package project.smartlocker.http.models.friends

data class FriendDTO(
    val user: Int,
    val friend: Int,
    val date: String,
    val status: String
)
