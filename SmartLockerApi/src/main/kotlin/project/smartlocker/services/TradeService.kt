package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.domain.trade.*
import project.smartlocker.http.models.trade.CreateTradeRequest
import project.smartlocker.http.models.trade.TradeDTO
import project.smartlocker.http.models.trade.UpdateTradeRequest
import project.smartlocker.repository.TradeRepository

@Service
class TradeService(
    private val tradeRepository: TradeRepository
){
    fun getTrades(): List<TradeDTO> {
        val trades = tradeRepository.getAllTrades()
        val outputs = trades.map {
            val status = tradeRepository.getTradeStatus(it.id)
            TradeDTO(it.id, it.senderId, it.receiverId, it.lockerId, it.startDate, it.endDate, status!!.read, status.status.toString())
        }
        return outputs
    }

    fun getTradeById(id: Int): TradeDTO? {
        val trade = tradeRepository.getTradeById(id)
        return if (trade != null) {
            val status = tradeRepository.getTradeStatus(id)
            TradeDTO(trade.id, trade.senderId, trade.receiverId, trade.lockerId, trade.startDate, trade.endDate, status!!.read, status.status.toString())
        }
        else{
            null
        }
    }

    fun createTrade(new: CreateTradeRequest) {
        val status = TradeEnum.PENDING.toString()
        val tradeId = tradeRepository.createTrade(new.senderId, new.receiverId, new.lockerId, new.startDate, new.endDate)
        tradeRepository.createTradeStatus(tradeId, false, status)
    }

    fun updateTrade(id:Int, trade:UpdateTradeRequest){
        val tradeId = tradeRepository.updateTrade(id, trade.senderId,trade.receiverId, trade.lockerId, trade.startDate, trade.endDate)
        tradeRepository.updateTradeStatus(tradeId, trade.read, trade.status)
    }

    fun deleteTrade(id:Int){
        val tradeId = tradeRepository.deleteTradeStatus(id)
        tradeRepository.deleteTrade(tradeId)
    }

}


