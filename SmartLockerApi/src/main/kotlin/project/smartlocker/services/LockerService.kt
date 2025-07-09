package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.domain.locker.LockerStatus
import project.smartlocker.http.models.hash.input.CreateHashRequest
import project.smartlocker.http.models.locker.input.CreateLockerRequest
import project.smartlocker.http.models.locker.input.UpdateLockerRequest
import project.smartlocker.http.models.locker.output.LockerDTO
import project.smartlocker.repository.HashRepository
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

    fun createLocker(new: CreateLockerRequest){
        val hash = UUID.randomUUID().toString().replace("-", "")
        lockerRepository.createLocker(new.module, hash, new.active)
        val input = CreateHashRequest(hash)
        hashService.createQrCode(input)
    }

    fun updateLocker(id: Int, input: UpdateLockerRequest) {
        val locker = lockerRepository.getLockerById(id)?: throw Exception("User not found")

        val module = input.module ?: locker.module
        val hash = input.hash ?: locker.hash
        val active = input.active ?: locker.active
        val status = input.status ?: locker.status

        val lockerId = lockerRepository.updateLocker(id, module, hash, active)
        lockerRepository.updateLockerStatus(lockerId, status)
    }

    fun deleteLocker(id: Int) {
        lockerRepository.deleteLocker(id)
    }

    // global
}