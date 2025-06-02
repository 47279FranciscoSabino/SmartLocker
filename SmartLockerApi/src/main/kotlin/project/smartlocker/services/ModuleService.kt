package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.domain.module.ModuleEnum
import project.smartlocker.http.models.module.CreateModuleRequest
import project.smartlocker.http.models.module.ModuleDTO
import project.smartlocker.http.models.module.UpdateModuleRequest
import project.smartlocker.repository.ModuleRepository


@Service
class ModuleService(
    private val moduleRepository: ModuleRepository
) {
    fun getAllModules(): List<ModuleDTO> {
        val modules = moduleRepository.getAllModules()
        val outputs = modules.map {
            val status = moduleRepository.getModuleStatus(it.id)
            ModuleDTO(it.id, it.location, it.maxN, status!!.locName, status.status.toString())
        }
        return outputs
    }

    fun getModuleById(id: Int): ModuleDTO? {
        val module = moduleRepository.getModuleById(id)
        return if (module != null) {
            val status = moduleRepository.getModuleStatus(id)
            ModuleDTO(module.id, module.location, module.maxN, status!!.locName, status.status.toString())
        }
        else{
            null
        }
    }

    fun createModule(new: CreateModuleRequest) {
        val location = "POINT(${new.longitude} ${new.latitude})"
        val moduleId = moduleRepository.createModule(location, new.maxN)
        moduleRepository.createModuleStatus(moduleId, ModuleEnum.AVAILABLE.toString(), new.locName)
    }

    fun updateModule(id: Int, module: UpdateModuleRequest) {
        val location = "POINT(${module.longitude} ${module.latitude})"
        val moduleId =moduleRepository.updateModule(id, location, module.maxN)
        moduleRepository.updateModuleStatus(moduleId, module.status, module.locName)
    }

    fun deleteModule(id: Int){
        val moduleId = moduleRepository.deleteModuleStatus(id)
        moduleRepository.deleteModule(moduleId)
    }

    fun getModulesByRadius(latitude: Double, longitude: Double,  radius: Double): List<ModuleDTO>{
        val modules = moduleRepository.getModulesByRadius(longitude, latitude, radius)
        val outputs = modules.map {
            val status = moduleRepository.getModuleStatus(it.id)
            ModuleDTO(it.id, it.location, it.maxN, status!!.locName, status.status.toString())
        }
        return outputs
    }
}
