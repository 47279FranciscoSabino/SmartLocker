package project.smartlocker.domain.friends

import project.smartlocker.domain.user.User

data class Friends(
    val user: Int,
    val friend: Int,
    val date: String
)