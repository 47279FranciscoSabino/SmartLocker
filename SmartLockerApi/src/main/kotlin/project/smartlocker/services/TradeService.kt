package project.smartlocker.services

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import project.smartlocker.domain.trade.*
import project.smartlocker.http.models.trade.input.CreateTradeRequest
import project.smartlocker.http.models.trade.input.UpdateTradeRequest
import project.smartlocker.http.models.trade.output.TradeDTO
import project.smartlocker.repository.LockerRepository
import project.smartlocker.repository.TradeRepository

@Service
class TradeService(
    private val tradeRepository: TradeRepository,
    private val lockerRepository: LockerRepository
){
    // admin
    fun getAllTrades(): List<TradeDTO> {
        return tradeRepository.getAllTrades()
    }

    fun getAllTradesStatus(): List<TradeStatus> {
        return tradeRepository.getAllTradesStatus()
    }

    fun deleteTrade(id:Int){
        tradeRepository.deleteTrade(id)
    }

    // global
    fun getTradeById(id: Int): TradeDTO? {
        return tradeRepository.getTradeById(id)
    }

    fun getPendingTrade(locker: Int): TradeDTO? {
        return tradeRepository.getPendingTrade(locker)
    }

    fun createTrade(new: CreateTradeRequest) {
        tradeRepository.createTrade(new.senderId, new.receiverId, new.lockerId)
    }

    fun updateTrade(id:Int, input: UpdateTradeRequest){
        val trade = tradeRepository.getTradeById(id)?: throw Exception("User not found")

        val read = input.read ?: trade.read
        val status = input.status ?: trade.status

        lockerRepository.updateLockerInfo(trade.lockerId, false)
        tradeRepository.updateTradeStatus(id, read, status)
    }
}