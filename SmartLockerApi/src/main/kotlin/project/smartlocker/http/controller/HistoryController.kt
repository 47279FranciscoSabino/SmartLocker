package project.smartlocker.http.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import project.smartlocker.http.models.trade.TradeDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.HistoryService
import project.smartlocker.services.TradeService

@RestController
@RequestMapping(Uris.HOME)
class HistoryController(
    private val historyService: HistoryService
) {

    @GetMapping(Uris.History.GET_BY_LOCKER)
    fun getLockerHistory(@PathVariable id: Int): ResponseEntity<List<TradeDTO>> {
        val history = historyService.getLockerHistory(id)
        return ResponseEntity.ok(history)
    }

    @GetMapping(Uris.History.USER_HISTORY)
    fun getUserHistory(@PathVariable id: Int): ResponseEntity<List<TradeDTO>> {
        val history = historyService.getUserHistory(id)
        return ResponseEntity.ok(history)
    }

    @GetMapping(Uris.History.GET_BY_SENDER)
    fun getBySender(@PathVariable id: Int): ResponseEntity<List<TradeDTO>> {
        val history = historyService.getSenderHistory(id)
        return ResponseEntity.ok(history)
    }

    @GetMapping(Uris.History.GET_BY_RECEIVER)
    fun getByReceiver(@PathVariable id: Int): ResponseEntity<List<TradeDTO>> {
        val history = historyService.getReceiverHistory(id)
        return ResponseEntity.ok(history)
    }
}
