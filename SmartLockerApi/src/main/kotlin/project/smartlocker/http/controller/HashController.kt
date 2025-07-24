package project.smartlocker.http.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.http.models.hash.input.CreateHashRequest
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.HashService
import project.smartlocker.services.LockerService

@RestController
@RequestMapping(Uris.API)
class HashController(
    private val hashService: HashService,
    private val lockerService: LockerService
) {
    //admin
    @PostMapping(Uris.Hash.CREATE_HASH)
    fun insertHash(
        @RequestBody insert: CreateHashRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        hashService.createQrCode(insert.hash)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Hash created successfully.")
    }
/*
    //global
    @GetMapping(Uris.Hash.GET_HASH)
    fun getHash(
        @PathVariable hash: String
    ): ResponseEntity<QrScanDTO> {
        val h = hashService.getHash(hash)
        return ResponseEntity.ok(h)
    }

 */

    // app
    @GetMapping(Uris.Hash.SCAN)
    fun scan(
        @PathVariable hash: String,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        if (request.getAttribute("authenticatedUser") !is UserDTO)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val realHash = hashService.getHash(hash) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid hash")

        val locker = lockerService.getLockerById(realHash.locker)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(locker)
    }
}
