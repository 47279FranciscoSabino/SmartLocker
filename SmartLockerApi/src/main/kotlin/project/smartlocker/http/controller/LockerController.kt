package project.smartlocker.http.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.locker.LockerStatus
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.http.models.locker.input.CreateLockerRequest
import project.smartlocker.http.models.locker.output.LockerDTO
import project.smartlocker.http.models.locker.input.UpdateLockerRequest
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.LockerService


@RestController
@RequestMapping(Uris.API)
class LockerController(
    private val lockerService: LockerService
) {
    // admin
    @GetMapping(Uris.Locker.GET_ALL_LOCKERS)
    fun getAllLockers(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        val lockers = lockerService.getAllLockers()
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(lockers)
    }

    @GetMapping(Uris.Locker.GET_ALL_LOCKERS_STATUS)
    fun getAllLockersStatus(): ResponseEntity<List<LockerStatus>> {
        val lockers = lockerService.getAllLockersStatus()
        return ResponseEntity.ok(lockers)
    }

    @PostMapping(Uris.Locker.CREATE_LOCKER)
    fun createLocker(
        @RequestBody input: CreateLockerRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        lockerService.createLocker(input.module, input.hash, input.active)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Locker created successfully.")
    }

    @PutMapping(Uris.Locker.UPDATE_LOCKER)
    fun updateLocker(
        @PathVariable id: Int,
        @RequestBody input: UpdateLockerRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        lockerService.updateLocker(id, input.module, input.hash, input.active, input.status)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Locker updated.")
    }

    @DeleteMapping(Uris.Locker.DELETE_LOCKER)
    fun deleteLocker(
        @PathVariable id: Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        lockerService.deleteLocker(id)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Locker deleted.")
    }

    //
    @GetMapping(Uris.Locker.GET_LOCKER_BY_ID)
    fun getLockerById(@PathVariable id: Int): ResponseEntity<LockerDTO> {
        val locker = lockerService.getLockerById(id)
        return locker?.let{
            ResponseEntity.ok(locker)
        }?: ResponseEntity.notFound().build()
    }

    @GetMapping(Uris.Locker.GET_LOCKER_BY_HASH)
    fun getLockerByHash(@PathVariable hash: String): ResponseEntity<LockerDTO> {
        val locker = lockerService.getLockerByHash(hash)
        return locker?.let{
            ResponseEntity.ok(locker)
        }?: ResponseEntity.notFound().build()

    }
}
