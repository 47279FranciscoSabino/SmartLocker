package project.smartlocker.http.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.http.models.trade.*
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.TradeService



@RestController
@RequestMapping(Uris.HOME)
class TradeController(
    private val tradeService: TradeService
){
    @GetMapping(Uris.Trade.GET_ALL_TRADE)
    fun getAllTrades(): ResponseEntity<List<TradeDTO>> {
        val trades = tradeService.getTrades()
        return ResponseEntity.ok(trades)
    }

    @GetMapping(Uris.Trade.GET_TRADE_BY_ID)
    fun getTradeById(@PathVariable id: Int): ResponseEntity<TradeDTO> {
        val trade = tradeService.getTradeById(id)
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

    @DeleteMapping(Uris.Trade.DELETE_TRADE)
    fun deleteTrade(@PathVariable id: Int): ResponseEntity<Void> {
        tradeService.deleteTrade(id)
        return ResponseEntity.noContent().build()
    }
}



