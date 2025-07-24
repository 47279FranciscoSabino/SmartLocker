package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.http.models.trade.output.TradeDTO
import project.smartlocker.http.models.trade.output.TradeInfoDTO
import project.smartlocker.repository.HistoryRepository
import project.smartlocker.repository.TradeRepository

@Service
class HistoryService(
    private val tradeRepository: TradeRepository,
    private val historyRepository: HistoryRepository
) {
    // admin
    fun getLockerHistory(lockerId:Int): List<TradeDTO> {
        return tradeRepository.getTradesByLocker(lockerId)
    }

    //--------------------------------------------------------------------------------------------------------
    fun getFullHistory(userId:Int): List<TradeInfoDTO> {
        return historyRepository.getFullHistory(userId)
    }

    fun getReceiverHistory(receiverId:Int): List<TradeInfoDTO> {
        return historyRepository.getReceiverHistory(receiverId)
    }

    fun getSenderHistory(senderId:Int): List<TradeInfoDTO> {
        return historyRepository.getSenderHistory(senderId)
    }
}