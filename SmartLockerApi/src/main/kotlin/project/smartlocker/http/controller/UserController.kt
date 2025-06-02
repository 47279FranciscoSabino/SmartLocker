package project.smartlocker.http.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.http.models.user.CreateUserRequest
import project.smartlocker.http.models.user.UpdateUserRequest
import project.smartlocker.http.models.user.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.UserService


@RestController
@RequestMapping(Uris.HOME)
class UserController(
    private val userService: UserService
) {
    @GetMapping(Uris.Users.GET_ALL_USERS)
    fun getAllUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping(Uris.Users.GET_BY_ID)
    fun getUserById(@PathVariable id: Int): ResponseEntity<UserDTO> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(user)
    }

    @PostMapping(Uris.Users.CREATE_USER)
    fun createUser(@RequestBody input: CreateUserRequest): ResponseEntity<Void> {
        userService.createUser(input)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping(Uris.Users.UPDATE_USER)
    fun updateUser(@PathVariable id: Int, @RequestBody input: UpdateUserRequest): ResponseEntity<UserDTO> {
        userService.updateUser(id, input)
        return ResponseEntity.ok().build()
    }

/*
    @DeleteMapping(Uris.Users.DELETE)
    fun deleteUser(@PathVariable id: Int): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping(Uris.Users.LOGIN)
    fun login(@RequestBody input: LoginDTO): ResponseEntity<TokenDTO> {
        val token = userService.login(input.username, input.password)
        return ResponseEntity.ok(TokenDTO(token))
    }
}
 */

}