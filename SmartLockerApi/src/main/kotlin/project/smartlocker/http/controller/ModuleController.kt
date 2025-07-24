package project.smartlocker.http.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.module.ModuleStatus
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.http.models.module.input.CreateModuleRequest
import project.smartlocker.http.models.module.input.UpdateModuleRequest
import project.smartlocker.http.models.module.output.AdminModuleDTO
import project.smartlocker.http.models.module.output.ModuleDTO
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.ModuleService

@RestController
@RequestMapping(Uris.API)
class ModuleController(
    private val moduleService: ModuleService
) {
    // admin
    @GetMapping(Uris.Module.GET_ALL_MODULES)
    fun getAllModules(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        val modules = moduleService.getAllModules()
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(modules)
    }

    @GetMapping(Uris.Module.GET_ALL_MODULES_STATUS)
    fun getAllModuleStatus(): ResponseEntity<List<ModuleStatus>> {
        val users = moduleService.getAllModulesStatus()
        return ResponseEntity.ok(users)
    }

    @PostMapping(Uris.Module.CREATE_MODULE)
    fun createModule(
        @RequestBody input: CreateModuleRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        moduleService.createModule(input.latitude, input.longitude, input.locName, input.maxN)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("New module created.")
    }

    @PutMapping(Uris.Module.UPDATE_MODULE)
    fun updateModule(
        @PathVariable id: Int,
        @RequestBody input: UpdateModuleRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        moduleService.updateModule(id, input.latitude, input.longitude, input.locName, input.maxN, input.status)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Module updated.")
    }

    @DeleteMapping(Uris.Module.DELETE_MODULE)
    fun deleteModule(
        @PathVariable id: Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        moduleService.deleteModule(id)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Module deleted.")

    }
    @GetMapping(Uris.Module.GET_MODULE_BY_ID)
    fun getModuleById(
        @PathVariable id: Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        val module = moduleService.getModuleById(id)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(module)
    }

    // app
    @GetMapping(Uris.Module.GET_MAP)
    fun getMap(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam radius: Double
    ): ResponseEntity<Any> {
        val modules = moduleService.getMap(latitude, longitude, radius)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(modules)
    }

}