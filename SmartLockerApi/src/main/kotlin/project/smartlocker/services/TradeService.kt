package project.smartlocker.services

import TradeInfoDTO
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
/*
    fun createTrade(new: CreateTradeRequest) {
        tradeRepository.createTrade(new.senderId, new.receiverId, new.lockerId)
    }

 */

    fun updateTrade(id:Int, input: UpdateTradeRequest){
        val trade = tradeRepository.getTradeById(id)?: throw Exception("User not found")

        val read = input.read ?: trade.read
        val status = input.status ?: trade.status

        lockerRepository.updateLockerInfo(trade.lockerId, "AVAILABLE")
        tradeRepository.updateTradeStatus(id, read, status)
    }

    // app
    fun getTrade(user: Int, id: Int): TradeInfoDTO {
        val trade = tradeRepository.getTrade(id) ?: throw Exception("Trade not found")

        if (user != trade.senderId && user != trade.receiverId) {
            throw Exception("You do not have access to this trade")
        }

        return trade
    }

    fun getLockerTrade(lockerId: Int): TradeDTO {
        return tradeRepository.getPendingTrade(lockerId)
            ?: throw Exception("No pending trade for locker $lockerId")
    }

    fun newTrade(user: Int, input: CreateTradeRequest) {
        if (lockerRepository.getLockerById(input.lockerId)== null) {
            throw Exception("Locker not found")
        }
        tradeRepository.createTrade(user, input.receiverId, input.lockerId)
    }

    fun editTrade(user: Int, id: Int, input: UpdateTradeRequest) {
        val trade = tradeRepository.getTradeById(id) ?: throw Exception("Trade not found")

        if (user != trade.senderId && user != trade.receiverId) {
            throw Exception("Only the sender or receiver can edit the trade")
        }

        val read = input.read ?: trade.read
        val status = input.status ?: trade.status
        if(!read){   //package withdraw
            lockerRepository.updateLockerInfo(trade.lockerId, "AVAILABLE")
        }
        tradeRepository.updateTradeStatus(id, read, status)
    }

}