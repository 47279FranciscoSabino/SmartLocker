package project.smartlocker.http.controller

import project.smartlocker.http.models.user.output.UserInfoDTO
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.domain.user.User
import project.smartlocker.http.models.user.input.*
import project.smartlocker.http.models.user.output.AdminUserStatusDTO
import project.smartlocker.http.models.user.output.TokenDTO
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
    ): Any {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }

        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
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
    ): Any {

        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        userService.deleteUser(id)
        return ResponseEntity.ok("User $id was deleted successfully.")
    }

    @PutMapping(Uris.Users.EDIT_ROLE)
    fun editUserRole(
        @PathVariable id: Int,
        @RequestBody update: UpdateUserRoleRequest,
        request: HttpServletRequest
    ): Any {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        userService.editRole(id, update)
        return ResponseEntity.ok("User $id got a new role.")
    }


    // global
    @GetMapping(Uris.Users.GET_BY_ID)
    fun getUserById(@PathVariable id: Int): ResponseEntity<UserDTO> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(user)
    }

    @GetMapping(Uris.Users.GET_BY_USERNAME)
    fun getUserByUsername(
        @PathVariable username: String,
        request: HttpServletRequest
    ): ResponseEntity<UserDTO> {
        request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        val user = userService.getUserByUsername(username)
        return ResponseEntity.ok(user)
    }

    @PutMapping(Uris.Users.EDIT_STATUS)
    fun editUserStatus(
        @PathVariable userId: Int,
        @RequestBody input: UpdateUserStatusRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        if (request.getAttribute("authenticatedUser") !is UserDTO) return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Unauthorized: Please log in to access this resource.")

        userService.editUserStatus(userId, input)
        return ResponseEntity.ok().build()
    }


    // app
    @PostMapping(Uris.Users.LOGIN)
    fun login(
        @RequestBody input: LoginRequest
    ): ResponseEntity<TokenDTO> {
        val token = userService.login(input)
        return ResponseEntity.ok(token)
    }

    @PostMapping(Uris.Users.LOGOUT)
    fun logout(
        request: HttpServletRequest
    ): ResponseEntity<Void> {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        userService.logout(user)
        return ResponseEntity.noContent().build()
    }

    @PostMapping(Uris.Users.REGISTER)
    fun register(
        @RequestBody input: CreateUserRequest
    ): ResponseEntity<Void> {
        userService.createUser(input)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }


    @GetMapping(Uris.Users.PROFILE)
    fun getProfile(
        request: HttpServletRequest
    ): ResponseEntity<UserDTO> {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        val profile = userService.getUserById(user.id)
        return ResponseEntity.ok(profile)
    }

    @PutMapping(Uris.Users.EDIT_USER)
    fun updateProfile(
        request: HttpServletRequest,
        @RequestBody update: UpdateUserRequest
    ): ResponseEntity<Void> {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        userService.updateUser(user.id, update)
        return ResponseEntity.noContent().build()
    }

    @GetMapping(Uris.Users.USER_INFO)
    fun getUserInfo(
        request: HttpServletRequest,
        @PathVariable id: Int
    ): ResponseEntity<UserInfoDTO> {
        request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")
        val user = userService.getUserInfo(id)
        return ResponseEntity.ok(user)
    }
}
