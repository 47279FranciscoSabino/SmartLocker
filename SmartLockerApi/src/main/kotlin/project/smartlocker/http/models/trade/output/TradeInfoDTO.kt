data class TradeInfoDTO(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val startDate: String,
    val endDate: String?,
    val status: String,
    val location: String
)