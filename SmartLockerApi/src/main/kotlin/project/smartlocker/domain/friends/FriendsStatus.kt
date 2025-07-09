package project.smartlocker.domain.friends

data class FriendsStatus(
    val user: Int,
    val friend: Int,
    val status: FriendsEnum
)

enum class FriendsEnum {
    PENDING, ACCEPTED, BLOCKED
}