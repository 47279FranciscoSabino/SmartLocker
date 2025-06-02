package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.domain.locker.LockerEnum
import project.smartlocker.domain.locker.LockerStatus
import project.smartlocker.http.models.locker.CreateLockerRequest
import project.smartlocker.http.models.locker.LockerDTO
import project.smartlocker.http.models.locker.UpdateLockerRequest
import project.smartlocker.repository.LockerRepository

@Service
class LockerService(
    private val lockerRepository: LockerRepository
) {
    fun getAllLockers(): List<LockerDTO> {
        val lockers = lockerRepository.getAllLockers()
        val outputs = lockers.map {
            val status = lockerRepository.getLockerStatus(it.id)
            LockerDTO(it.id, it.module, it.qr, it.active, status.status.toString())
        }
        return outputs
    }

    fun getLockerById(id: Int): LockerDTO? {
        val locker = lockerRepository.getLockerById(id)
        locker?.let {
            val status = lockerRepository.getLockerStatus(it.id)
            return LockerDTO(it.id, it.module, it.qr, it.active, status.status.toString())
        }
        return null
    }

    fun createLocker(new: CreateLockerRequest){
        val lockerId = lockerRepository.createLocker(new.module, new.qr, new.active)
        val status = LockerEnum.AVAILABLE.toString()
        lockerRepository.createLockerStatus(lockerId, status)
    }

    fun updateLocker(id: Int, locker: UpdateLockerRequest) {
        val lockerId = lockerRepository.updateLocker(id, locker.module, locker.qr, locker.active)
        lockerRepository.updateLockerStatus(lockerId, locker.status)
    }

    fun deleteLocker(id: Int) {
        val lockerId = lockerRepository.deleteLockerStatus(id)
        lockerRepository.deleteLocker(lockerId)
    }
}