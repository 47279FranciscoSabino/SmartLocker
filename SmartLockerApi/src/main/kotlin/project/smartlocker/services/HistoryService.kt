package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.http.models.trade.output.TradeDTO
import project.smartlocker.repository.TradeRepository


@Service
class HistoryService(
    private val tradeRepository: TradeRepository
) {
    // admin
    fun getLockerHistory(lockerId:Int): List<TradeDTO> {
        return tradeRepository.getTradesByLocker(lockerId)
    }

    // global
    fun getReceiverHistory(receiverId:Int): List<TradeDTO> {
        return tradeRepository.getTradesByReceiver(receiverId)
    }

    fun getSenderHistory(senderId:Int): List<TradeDTO> {
        return tradeRepository.getTradesBySender(senderId)
    }

    fun getUserHistory(userId:Int): List<TradeDTO> {
        return tradeRepository.getUserTrades(userId)
    }
}