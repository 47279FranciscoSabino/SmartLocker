package project.smartlocker.http.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.http.models.module.CreateModuleRequest
import project.smartlocker.http.models.module.ModuleDTO
import project.smartlocker.http.models.module.UpdateModuleRequest
import project.smartlocker.http.models.user.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.ModuleService

@RestController
@RequestMapping(Uris.HOME)
class ModuleController(
    private val moduleService: ModuleService
) {
    @GetMapping(Uris.Module.GET_ALL_MODULES)
    fun getAllModules(): ResponseEntity<List<ModuleDTO>> {
        val users = moduleService.getAllModules()
        return ResponseEntity.ok(users)
    }

    @GetMapping(Uris.Module.GET_MODULE_BY_ID)
    fun getModuleById(@PathVariable id: Int): ResponseEntity<ModuleDTO> {
        val module = moduleService.getModuleById(id)
        return ResponseEntity.ok(module)
    }

    @PostMapping(Uris.Module.CREATE_MODULE)
    fun createModule(@RequestBody input: CreateModuleRequest): ResponseEntity<Void> {
        moduleService.createModule(input)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping(Uris.Module.UPDATE_MODULE)
    fun updateModule(@PathVariable id: Int, @RequestBody input: UpdateModuleRequest): ResponseEntity<UserDTO> {
        moduleService.updateModule(id, input)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping(Uris.Module.DELETE_MODULE)
    fun deleteModule(@PathVariable id: Int): ResponseEntity<Void> {
        moduleService.deleteModule(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping(Uris.Module.GET_MODULE_GEO)
    fun getModulesByRadius(@PathVariable latitude: Double, @PathVariable longitude: Double, @RequestParam radius: Double): ResponseEntity<List<ModuleDTO>> {
        val modules = moduleService.getModulesByRadius(latitude, longitude, radius)
        return ResponseEntity.ok(modules)
    }
}
