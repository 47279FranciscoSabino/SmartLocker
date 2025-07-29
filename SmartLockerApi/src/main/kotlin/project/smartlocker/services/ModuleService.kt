package project.smartlocker.services

import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import project.smartlocker.domain.module.ModuleStatus
import project.smartlocker.http.models.module.output.AdminModuleDTO
import project.smartlocker.http.models.module.output.ModuleDTO
import project.smartlocker.repository.ModuleRepository


@Service
class ModuleService(
    private val moduleRepository: ModuleRepository
) {
    // admin
    fun getAllModules(): List<AdminModuleDTO> {
        return moduleRepository.getAllModules()
    }

    fun getAllModulesStatus(): List<ModuleStatus> {
        return moduleRepository.getAllModulesStatus()
    }

    fun getModuleById(id: Int): AdminModuleDTO? {
        return moduleRepository.getModuleById(id)
    }

    fun createModule(longitude: Double, latitude: Double, locName: String, maxN: Int) {
        val location = "POINT(${longitude} ${latitude})"
        moduleRepository.createModule(location, locName, maxN)
    }

    fun updateModule(id: Int, latitude: Double? = null, longitude: Double? = null, locName: String? = null, maxN: Int? = null, status: String? = null) {
        val module = moduleRepository.getModuleById(id)?: throw Exception("Module not found")

        val location = if( longitude == null || latitude == null){
            "POINT(${module.location.longitude} ${module.location.latitude})"
        }else{
            "POINT(${longitude} ${latitude})"
        }
        val newLocName = locName ?: module.locName
        val newMaxN = maxN ?: module.maxN
        val newStatus = status ?: module.status

        val moduleId = moduleRepository.updateModule(id, location, newLocName, newMaxN)
        moduleRepository.updateModuleStatus(moduleId, newStatus)
    }

    fun deleteModule(id: Int){
        moduleRepository.getModuleById(id)?: throw Exception("User not found")
        moduleRepository.deleteModule(id)
    }


    // app
    fun getMap(latitude: Double, longitude: Double,  radius: Double): List<ModuleDTO>{
        if (latitude !in -90.0..90.0) {
            throw BadRequestException("Latitude must be between -90 and 90.")
        }
        if (longitude !in -180.0..180.0) {
            throw BadRequestException("Longitude must be between -180 and 180.")
        }
        if (radius <= 0) {
            throw BadRequestException("Radius must be greater than 0.")
        }
        val modules = moduleRepository.getMap(longitude, latitude, radius)
        return modules
    }
}