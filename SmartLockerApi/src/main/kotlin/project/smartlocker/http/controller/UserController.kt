package project.smartlocker.http.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.http.models.user.input.*
import project.smartlocker.http.models.user.output.AdminUserStatusDTO
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.UserService


@RestController
@RequestMapping(Uris.API)
class UserController(
    private val userService: UserService
) {
    // admin
    @GetMapping(Uris.Users.GET_ALL_USERS)
    fun getAllUsers(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }

        val users = userService.getAllUsers()
        return ResponseEntity.status(HttpStatus.OK)
            .body(users)
    }

    @GetMapping(Uris.Users.GET_ALL_USERS_STATUS)
    fun getAllUsersStatus(): ResponseEntity<List<AdminUserStatusDTO>> {
        val status = userService.getAllUsersStatus()
        return ResponseEntity.ok(status)
    }

    @DeleteMapping(Uris.Users.DELETE)
    fun deleteUser(
        @PathVariable id: Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        userService.deleteUser(id)
        return ResponseEntity.status(HttpStatus.OK)
            .body("User $id was deleted successfully.")
    }

    @PutMapping(Uris.Users.EDIT_ROLE)
    fun editUserRole(
        @PathVariable id: Int,
        @RequestBody update: UpdateUserRoleRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        userService.editRole(id, update.role)
        return ResponseEntity.status(HttpStatus.OK)
            .body("User $id got a new role.")
    }


    // global
    @GetMapping(Uris.Users.GET_BY_ID)
    fun getUserById(@PathVariable id: Int): ResponseEntity<UserDTO> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(user)
    }

    @GetMapping(Uris.Users.GET_BY_USERNAME)
    fun getUserByUsername(
        @RequestParam username: String,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        if (request.getAttribute("authenticatedUser") !is UserDTO)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Unauthorized: Please log in to access this resource.")

        val user = userService.getUserByUsername(username)
        return ResponseEntity.status(HttpStatus.OK)
            .body(user)
    }

    @PutMapping(Uris.Users.EDIT_STATUS)
    fun editUserStatus(
        @PathVariable userId: Int,
        @RequestBody input: UpdateUserStatusRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        if (request.getAttribute("authenticatedUser") !is UserDTO)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Unauthorized: Please log in to access this resource.")

        userService.editUserStatus(userId, input.status)
        return ResponseEntity.status(HttpStatus.OK)
            .body("User status updated.")
    }


    // app
    @PostMapping(Uris.Users.LOGIN)
    fun login(
        @RequestBody input: LoginRequest
    ): ResponseEntity<Any> {
        val token = userService.login(input.email, input.password)
        return ResponseEntity.status(HttpStatus.OK)
            .body(token)
    }

    @PostMapping(Uris.Users.LOGOUT)
    fun logout(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")
        userService.logout(user.id)
        return ResponseEntity.status(HttpStatus.OK)
            .body("Successfully logged out.")
    }

    @PostMapping(Uris.Users.REGISTER)
    fun register(
        @RequestBody input: CreateUserRequest
    ): ResponseEntity<Any> {
        userService.createUser(input.username, input.email, input.password)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Successfully created.")
    }

    @GetMapping(Uris.Users.PROFILE)
    fun getProfile(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val profile = userService.getUserById(user.id)
        return ResponseEntity.status(HttpStatus.OK)
            .body(profile)
    }

    @PutMapping(Uris.Users.EDIT_USER)
    fun updateProfile(
        request: HttpServletRequest,
        @RequestBody update: UpdateUserRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")
        userService.updateUser(user.id, update.email, update.password)
        return ResponseEntity.status(HttpStatus.OK)
            .body("User updated successfully.")
    }

    @GetMapping(Uris.Users.USER_INFO)
    fun getUserInfo(
        request: HttpServletRequest,
        @PathVariable id: Int
    ): ResponseEntity<Any> {
        if (request.getAttribute("authenticatedUser") !is UserDTO)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Unauthorized: Please log in to access this resource.")
        val user = userService.getUserInfo(id)
        return ResponseEntity.status(HttpStatus.OK)
            .body(user)
    }
}
