package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.http.models.trade.TradeDTO
import project.smartlocker.repository.TradeRepository


@Service
class HistoryService(
    private val tradeRepository: TradeRepository
) {
    fun getLockerHistory(lockerId:Int): List<TradeDTO> {
        val trades = tradeRepository.getTradesByLocker(lockerId)
        val outputs = trades.map {
            val status = tradeRepository.getTradeStatus(it.id)
            TradeDTO(it.id, it.senderId, it.receiverId, it.lockerId, it.startDate, it.endDate, status!!.read, status.status.toString())
        }
        return outputs
    }

    fun getReceiverHistory(receiverId:Int): List<TradeDTO> {
        val trades = tradeRepository.getTradesByReceiver(receiverId)
        val outputs = trades.map {
            val status = tradeRepository.getTradeStatus(it.id)
            TradeDTO(it.id, it.senderId, it.receiverId, it.lockerId, it.startDate, it.endDate, status!!.read, status.status.toString())
        }
        return outputs
    }

    fun getSenderHistory(senderId:Int): List<TradeDTO> {
        val trades = tradeRepository.getTradesBySender(senderId)
        val outputs = trades.map {
            val status = tradeRepository.getTradeStatus(it.id)
            TradeDTO(it.id, it.senderId, it.receiverId, it.lockerId, it.startDate, it.endDate, status!!.read, status.status.toString())
        }
        return outputs
    }

    fun getUserHistory(userId:Int): List<TradeDTO> {
        val trades = tradeRepository.getUserTrades(userId)
        val outputs = trades.map {
            val status = tradeRepository.getTradeStatus(it.id)
            TradeDTO(it.id, it.senderId, it.receiverId, it.lockerId, it.startDate, it.endDate, status!!.read, status.status.toString())
        }
        return outputs
    }
}