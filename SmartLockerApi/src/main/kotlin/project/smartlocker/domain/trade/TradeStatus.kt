package project.smartlocker.domain.trade

data class TradeStatus(
    val trade: Int,
    val read: Boolean,
    val status: TradeEnum
)

enum class TradeEnum{
    PENDING, COMPLETED, CANCELLED
}