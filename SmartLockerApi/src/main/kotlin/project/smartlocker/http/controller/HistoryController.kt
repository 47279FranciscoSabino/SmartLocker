package project.smartlocker.http.controller

import TradeInfoDTO
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import project.smartlocker.domain.user.User
import project.smartlocker.http.models.trade.output.TradeDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.HistoryService

@RestController
@RequestMapping(Uris.API)
class HistoryController(
    private val historyService: HistoryService
) {
    // admin
    @GetMapping(Uris.History.GET_BY_LOCKER)
    fun getLockerHistory(@PathVariable id: Int): ResponseEntity<List<TradeDTO>> {
        val history = historyService.getLockerHistory(id)
        return ResponseEntity.ok(history)
    }

    // global
    @GetMapping(Uris.History.USER_HISTORY)
    fun getUserHistory(@PathVariable id: Int): ResponseEntity<List<TradeDTO>> {
        val history = historyService.getUserHistory(id)
        return ResponseEntity.ok(history)
    }

    @GetMapping(Uris.History.GET_BY_SENDER)
    fun getBySender(@PathVariable id: Int): ResponseEntity<List<TradeDTO>> {
        val history = historyService.getBySender(id)
        return ResponseEntity.ok(history)
    }

    @GetMapping(Uris.History.GET_BY_RECEIVER)
    fun getByReceiver(@PathVariable id: Int): ResponseEntity<List<TradeDTO>> {
        val history = historyService.getByReceiver(id)
        return ResponseEntity.ok(history)
    }

    // app
    @GetMapping(Uris.History.USER_FULL_HISTORY)
    fun getFullHistory(
        request: HttpServletRequest
    ): ResponseEntity<List<TradeInfoDTO>> {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        val history = historyService.getFullHistory(user.id)
        return ResponseEntity.ok(history)
    }

    @GetMapping(Uris.History.SENDER_HISTORY)
    fun getSenderHistory(
        request: HttpServletRequest
    ): ResponseEntity<List<TradeInfoDTO>>  {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        val history = historyService.getSenderHistory(user.id)
        return ResponseEntity.ok(history)
    }

    @GetMapping(Uris.History.RECEIVER_HISTORY)
    fun getReceiverHistory(
        request: HttpServletRequest
    ): ResponseEntity<List<TradeInfoDTO>>  {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        val history = historyService.getReceiverHistory(user.id)
        return ResponseEntity.ok(history)
    }
}
