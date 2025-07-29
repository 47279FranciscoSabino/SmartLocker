package project.smartlocker.http.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.trade.TradeStatus
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.http.models.trade.input.CreateTradeRequest
import project.smartlocker.http.models.trade.input.UpdateTradeRequest
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.LockerService
import project.smartlocker.services.TradeService

@RestController
@RequestMapping(Uris.API)
class TradeController(
    private val hardwareController:HardwareController,
    private val tradeService: TradeService,
    private val lockerService: LockerService,
){
    // admin
    @GetMapping(Uris.Trade.GET_ALL_TRADE)
    fun getAllTrades(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }

        val trades = tradeService.getAllTrades()
        return ResponseEntity.status(HttpStatus.OK)
            .body(trades)
    }

    @GetMapping(Uris.Trade.GET_ALL_TRADE_STATUS)
    fun getAllTradesStatus(): ResponseEntity<List<TradeStatus>> {
        val trades = tradeService.getAllTradesStatus()
        return ResponseEntity.ok(trades)
    }

    @DeleteMapping(Uris.Trade.DELETE_TRADE)
    fun deleteTrade(
        @PathVariable id: Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        tradeService.deleteTrade(id)
        return ResponseEntity.status(HttpStatus.OK)
            .body("Trade deleted successfully.")
    }

    //app
    @GetMapping(Uris.Trade.GET_TRADE)
    fun getTrade(
        request: HttpServletRequest,
        @PathVariable tradeId: Int
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val trade = tradeService.getTrade(user.id, tradeId)
        return ResponseEntity.status(HttpStatus.OK)
            .body(trade)
    }

    @GetMapping(Uris.Trade.LOCKER_TRADE)
    fun getLockerTrade(
        request: HttpServletRequest,
        @PathVariable lockerId: Int
    ): ResponseEntity<Any> {
        if (request.getAttribute("authenticatedUser") !is UserDTO)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val trade = tradeService.getLockerTrade(lockerId)
        return ResponseEntity.status(HttpStatus.OK)
            .body(trade)
    }

    @PostMapping(Uris.Trade.NEW_TRADE)
    fun newTrade(
        request: HttpServletRequest,
        @RequestBody input: CreateTradeRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val locker = lockerService.getLockerById(input.lockerId)
        if (locker != null) {
            hardwareController.newTrade(locker.ip, true, false, "open")
        }

        tradeService.newTrade(user.id, input.receiverId, input.lockerId)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Trade created successfully.")
    }

    @PutMapping(Uris.Trade.EDIT_TRADE)
    fun editTrade(
        request: HttpServletRequest,
        @PathVariable tradeId: Int,
        @RequestBody input: UpdateTradeRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        tradeService.editTrade(user.id, tradeId, input.read, input.status)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Trade status updated.")
    }

    @PutMapping(Uris.Trade.WITHDRAW)
    fun withdrawTrade(
        request: HttpServletRequest,
        @PathVariable lockerId: Int
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val trade = tradeService.getLockerTrade(lockerId)
        if (user.id == trade.senderId||user.id == trade.receiverId){

            val locker = lockerService.getLockerById(lockerId)
            if (locker != null) {
                hardwareController.newTrade(locker.ip, false, true, "open")
            }

            var status = "COMPLETED"
            if (user.id == trade.senderId) status = "CANCELLED"
            tradeService.editTrade(user.id, trade.id, false, status)

            return ResponseEntity.status(HttpStatus.OK)
                .body("Trade was withdrawn successfully.")
        }else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                .body("Trade was not withdrawn.")
        }
    }
}