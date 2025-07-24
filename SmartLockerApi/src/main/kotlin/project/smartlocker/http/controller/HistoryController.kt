package project.smartlocker.http.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.HistoryService

@RestController
@RequestMapping(Uris.API)
class HistoryController(
    private val historyService: HistoryService
) {
    // admin
    @GetMapping(Uris.History.GET_BY_LOCKER)
    fun getLockerHistory(
        @PathVariable id: Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        val history = historyService.getLockerHistory(id)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(history)
    }

    // app
    @GetMapping(Uris.History.USER_FULL_HISTORY)
    fun getFullHistory(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val history = historyService.getFullHistory(user.id)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(history)
    }

    @GetMapping(Uris.History.SENDER_HISTORY)
    fun getSenderHistory(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val history = historyService.getSenderHistory(user.id)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(history)
    }

    @GetMapping(Uris.History.RECEIVER_HISTORY)
    fun getReceiverHistory(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val history = historyService.getReceiverHistory(user.id)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(history)
    }
}
