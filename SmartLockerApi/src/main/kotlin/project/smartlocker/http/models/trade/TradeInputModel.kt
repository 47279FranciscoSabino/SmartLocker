package project.smartlocker.http.models.trade


data class CreateTradeRequest(
    val senderId: Int,
    val receiverId: Int,
    val lockerId: Int,
    val startDate: String
)

data class UpdateTradeRequest(
    val senderId: Int,
    val receiverId: Int,
    val lockerId: Int,
    val startDate: String,
    val endDate: String?,
    val read: Boolean,
    val status: String
)