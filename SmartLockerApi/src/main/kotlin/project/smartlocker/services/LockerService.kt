package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.domain.locker.LockerStatus
import project.smartlocker.http.models.locker.output.LockerDTO
import project.smartlocker.repository.LockerRepository
import java.util.*

@Service
class LockerService(
    private val lockerRepository: LockerRepository,
    private val hashService: HashService
) {
    // admin
    fun getAllLockers(): List<LockerDTO> {
        return lockerRepository.getAllLockers()
    }

    fun getAllLockersStatus(): List<LockerStatus> {
        return lockerRepository.getLockerStatus()
    }

    fun getLockerById(id: Int): LockerDTO? {
        return lockerRepository.getLockerById(id)
    }

    fun getLockerByHash(hash: String): LockerDTO? {
        return lockerRepository.getLockerByHash(hash)
    }

    fun createLocker(module: Int, hash: String, active: Boolean){
        val newHash = UUID.randomUUID().toString().replace("-", "")
        lockerRepository.createLocker(module, newHash, active)
        hashService.createQrCode(newHash)
    }

    fun updateLocker(id: Int, module: Int? = null, hash: String? = null, active: Boolean? = null, status: String? = null){
        val locker = lockerRepository.getLockerById(id)?: throw Exception("User not found")

        val newModule = module ?: locker.module
        val newHash = hash ?: locker.hash
        val newActive = active ?: locker.active
        val newStatus = status ?: locker.status

        val lockerId = lockerRepository.updateLocker(id, newModule, newHash, newActive)
        lockerRepository.updateLockerStatus(lockerId, newStatus)
    }

    fun deleteLocker(id: Int) {
        lockerRepository.deleteLocker(id)
    }

    // global
}