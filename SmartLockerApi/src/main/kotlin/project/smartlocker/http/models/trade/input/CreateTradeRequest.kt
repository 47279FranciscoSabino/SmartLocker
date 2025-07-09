package project.smartlocker.http.models.trade.input

data class CreateTradeRequest(
    val senderId: Int,
    val receiverId: Int,
    val lockerId: Int
)