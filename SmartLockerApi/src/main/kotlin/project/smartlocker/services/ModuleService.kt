package project.smartlocker.services

import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import project.smartlocker.domain.module.ModuleStatus
import project.smartlocker.http.models.module.input.CreateModuleRequest
import project.smartlocker.http.models.module.input.UpdateModuleRequest
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

    fun createModule(new: CreateModuleRequest) {
        val location = "POINT(${new.longitude} ${new.latitude})"
        moduleRepository.createModule(location, new.locName, new.maxN)
    }

    fun updateModule(id: Int, input: UpdateModuleRequest) {
        val module = moduleRepository.getModuleById(id)?: throw Exception("Module not found")

        val location = if( input.longitude == null || input.latitude == null){
            "POINT(${module.location.longitude} ${module.location.latitude})"
        }else{
            "POINT(${input.longitude} ${input.latitude})"
        }
        val locName = input.locName ?: module.locName
        val maxN = input.maxN ?: module.maxN
        val status = input.status ?: module.status

        val moduleId = moduleRepository.updateModule(id, location, locName, maxN)
        moduleRepository.updateModuleStatus(moduleId, status)
    }

    fun deleteModule(id: Int){
        moduleRepository.getModuleById(id)?: throw Exception("User not found")
        moduleRepository.deleteModule(id)
    }

/*
    // global
    fun getModulesByRadius(latitude: Double, longitude: Double,  radius: Double): List<ModuleDTO>{
        val modules = moduleRepository.getModulesByRadius(longitude, latitude, radius)
        return modules
    }
 */

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