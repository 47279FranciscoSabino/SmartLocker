package project.smartlocker.http.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.trade.TradeStatus
import project.smartlocker.http.models.trade.input.CreateTradeRequest
import project.smartlocker.http.models.trade.input.UpdateTradeRequest
import project.smartlocker.http.models.trade.output.TradeDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.TradeService

@RestController
@RequestMapping(Uris.HOME)
class TradeController(
    private val tradeService: TradeService
){
    // admin
    @GetMapping(Uris.Trade.GET_ALL_TRADE)
    fun getAllTrades(): ResponseEntity<List<TradeDTO>> {
        val trades = tradeService.getAllTrades()
        return ResponseEntity.ok(trades)
    }

    @GetMapping(Uris.Trade.GET_ALL_TRADE_STATUS)
    fun getAllTradesStatus(): ResponseEntity<List<TradeStatus>> {
        val trades = tradeService.getAllTradesStatus()
        return ResponseEntity.ok(trades)
    }

    @DeleteMapping(Uris.Trade.DELETE_TRADE)
    fun deleteTrade(@PathVariable id: Int): ResponseEntity<Void> {
        tradeService.deleteTrade(id)
        return ResponseEntity.noContent().build()
    }

    // global
    @GetMapping(Uris.Trade.GET_TRADE_BY_ID)
    fun getTradeById(@PathVariable id: Int): ResponseEntity<TradeDTO> {
        val trade = tradeService.getTradeById(id)
        return trade?.let {
            ResponseEntity.ok(trade)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping(Uris.Trade.GET_PENDING_TRADE)
    fun getPendingTrade(@PathVariable locker: Int): ResponseEntity<TradeDTO> {
        val trade = tradeService.getPendingTrade(locker)
        return trade?.let {
            ResponseEntity.ok(trade)
        } ?: ResponseEntity.notFound().build()
    }

    @PostMapping(Uris.Trade.CREATE_TRADE)
    fun createTrade(@RequestBody input: CreateTradeRequest): ResponseEntity<Void> {
        tradeService.createTrade(input)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping(Uris.Trade.UPDATE_TRADE)
    fun updateTrade(@PathVariable id: Int, @RequestBody input: UpdateTradeRequest): ResponseEntity<Void> {
        tradeService.updateTrade(id, input)
        return ResponseEntity.ok().build()
    }

}