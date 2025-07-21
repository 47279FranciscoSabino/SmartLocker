package project.smartlocker.http.controller

import com.example.smartlockerandroid.data.model.hash.output.QrScanDTO
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.user.User
import project.smartlocker.http.models.hash.input.CreateHashRequest
import project.smartlocker.http.models.locker.output.LockerDTO
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
    fun insertHash(@RequestBody insert: CreateHashRequest){
        hashService.createQrCode(insert)
    }

    //global
    @GetMapping(Uris.Hash.GET_HASH)
    fun getHash(@PathVariable hash: String): ResponseEntity<QrScanDTO> {
        val h = hashService.getHash(hash)
        return ResponseEntity.ok(h)
    }

    // app
    @GetMapping(Uris.Hash.SCAN)
    fun scan(
        @PathVariable hash: String,
        request: HttpServletRequest
    ): Any {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val h = hashService.getHash(hash)
        return h?.let {
            val locker = lockerService.getLockerByHash(it.hash)
            logger.info(locker.toString())
            ResponseEntity.ok(locker)
        }?: ResponseEntity.notFound()
    }
}
private val logger = LoggerFactory.getLogger("main")
