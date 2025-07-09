package project.smartlocker.http.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.locker.LockerStatus
import project.smartlocker.http.models.locker.input.CreateLockerRequest
import project.smartlocker.http.models.locker.output.LockerDTO
import project.smartlocker.http.models.locker.input.UpdateLockerRequest
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.LockerService


@RestController
@RequestMapping(Uris.HOME)
class LockerController(
    private val lockerService: LockerService
) {
    // admin
    @GetMapping(Uris.Locker.GET_ALL_LOCKERS)
    fun getAllLockers(): ResponseEntity<List<LockerDTO>> {
        val lockers = lockerService.getAllLockers()
        return ResponseEntity.ok(lockers)
    }

    @GetMapping(Uris.Locker.GET_ALL_LOCKERS_STATUS)
    fun getAllLockersStatus(): ResponseEntity<List<LockerStatus>> {
        val lockers = lockerService.getAllLockersStatus()
        return ResponseEntity.ok(lockers)
    }

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

    @PostMapping(Uris.Locker.CREATE_LOCKER)
    fun createLocker(@RequestBody input: CreateLockerRequest): ResponseEntity<Void> {
        lockerService.createLocker(input)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping(Uris.Locker.UPDATE_LOCKER)
    fun updateLocker(@PathVariable id: Int, @RequestBody input: UpdateLockerRequest): ResponseEntity<Void> {
        lockerService.updateLocker(id, input)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping(Uris.Locker.DELETE_LOCKER)
    fun deleteLocker(@PathVariable id: Int): ResponseEntity<Void> {
        lockerService.deleteLocker(id)
        return ResponseEntity.noContent().build()
    }

    // global
    /*
    @GetMapping(Uris.Locker.QR_VALIDATION)
    fun validateQR(@PathVariable id: Int): ResponseEntity<QrValidationResultDTO> {
        val result = lockerService.validateQrCode(id)
        return ResponseEntity.ok(result)
    }

     */
}
