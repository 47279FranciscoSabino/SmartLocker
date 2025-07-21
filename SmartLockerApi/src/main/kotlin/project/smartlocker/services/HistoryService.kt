package project.smartlocker.services

import TradeInfoDTO
import org.springframework.stereotype.Service
import project.smartlocker.http.models.trade.output.TradeDTO
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

    // global
    fun getByReceiver(receiverId:Int): List<TradeDTO> {
        return tradeRepository.getTradesByReceiver(receiverId)
    }

    fun getBySender(senderId:Int): List<TradeDTO> {
        return tradeRepository.getTradesBySender(senderId)
    }

    fun getUserHistory(userId:Int): List<TradeDTO> {
        return tradeRepository.getUserTrades(userId)
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