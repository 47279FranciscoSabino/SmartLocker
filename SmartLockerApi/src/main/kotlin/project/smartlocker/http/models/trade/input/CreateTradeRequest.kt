package project.smartlocker.http.models.trade.input

data class CreateTradeRequest(
    val receiverId: Int,
    val lockerId: Int
)