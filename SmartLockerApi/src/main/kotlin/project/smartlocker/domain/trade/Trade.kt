package project.smartlocker.domain.trade

data class Trade(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val lockerId: Int,
    val startDate: String,
    val endDate: String?,
)