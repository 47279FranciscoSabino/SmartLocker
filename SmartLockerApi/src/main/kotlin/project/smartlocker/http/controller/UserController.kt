package project.smartlocker.http.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.http.models.user.input.CreateUserRequest
import project.smartlocker.http.models.user.input.UpdateUserRequest
import project.smartlocker.http.models.user.input.UpdateUserStatusRequest
import project.smartlocker.http.models.user.output.AdminUserDTO
import project.smartlocker.http.models.user.output.AdminUserStatusDTO
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.UserService


@RestController
@RequestMapping(Uris.HOME)
class UserController(
    private val userService: UserService
) {
    // admin
    @GetMapping(Uris.Users.GET_ALL_USERS)
    fun getAllUsers(): ResponseEntity<List<AdminUserDTO>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping(Uris.Users.GET_ALL_USERS_STATUS)
    fun getAllUsersStatus(): ResponseEntity<List<AdminUserStatusDTO>> {
        val status = userService.getAllUsersStatus()
        return ResponseEntity.ok(status)
    }

    @DeleteMapping(Uris.Users.DELETE)
    fun deleteUser(@PathVariable id: Int): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    // global
    @GetMapping(Uris.Users.GET_BY_ID)
    fun getUserById(@PathVariable id: Int): ResponseEntity<UserDTO> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(user)
    }

    @GetMapping(Uris.Users.GET_BY_USERNAME)
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<UserDTO>{
        val user = userService.getUserByUsername(username)
        return ResponseEntity.ok(user)
    }

    @PutMapping(Uris.Users.UPDATE_USER)
    fun updateUser(@PathVariable id: Int, @RequestBody input: UpdateUserRequest): ResponseEntity<UserDTO> {
        userService.updateUser(id, input)
        return ResponseEntity.ok().build()
    }

    @PutMapping(Uris.Users.VALIDATE_USER)
    fun validateUser(@PathVariable id: Int, @RequestBody input: UpdateUserStatusRequest): ResponseEntity<UserDTO> {
        userService.validateUser(id, input)
        return ResponseEntity.ok().build()
    }

    @PostMapping(Uris.Users.CREATE_USER)
    fun createUser(@RequestBody input: CreateUserRequest): ResponseEntity<Void> {
        userService.createUser(input)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
/*
    @PostMapping(Uris.Users.LOGIN)
    fun login(@RequestBody input: LoginDTO): ResponseEntity<TokenDTO> {
        val token = userService.login(input.username, input.password)
        return ResponseEntity.ok(TokenDTO(token))
    }
*/