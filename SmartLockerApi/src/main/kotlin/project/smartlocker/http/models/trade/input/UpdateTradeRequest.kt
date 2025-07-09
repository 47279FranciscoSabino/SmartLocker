package project.smartlocker.http.models.trade.input

data class UpdateTradeRequest(
    val read: Boolean? = null,
    val status: String? = null
)