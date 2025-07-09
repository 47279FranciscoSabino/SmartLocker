package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.http.models.user.input.CreateUserRequest
import project.smartlocker.http.models.user.input.UpdateUserRequest
import project.smartlocker.http.models.user.input.UpdateUserStatusRequest
import project.smartlocker.http.models.user.output.AdminUserDTO
import project.smartlocker.http.models.user.output.AdminUserStatusDTO
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository
) {
    // admin
    fun getAllUsers(): List<AdminUserDTO> {
        val users = userRepository.getAllUsers()
        return users
    }

    fun getAllUsersStatus(): List<AdminUserStatusDTO> {
        val status = userRepository.getAllUsersStatus()
        return status
    }

    fun deleteUser(id: Int){
        userRepository.deleteUser(id)
    }

    // global
    fun getUserById(id: Int): UserDTO? {
        return userRepository.getUserById(id)
    }

    fun getUserByUsername(name:String):UserDTO?{
        return userRepository.getUserByUsername(name)
    }

    fun updateUser(id: Int, input: UpdateUserRequest) {
        val user = userRepository.getUser(id) ?: throw Exception("User not found")

        val newEmail = input.email ?: user.email
        val newPassword = input.password ?: user.password

        userRepository.updateUser(id, newEmail, newPassword)
    }

    fun validateUser(id: Int, input: UpdateUserStatusRequest){
        userRepository.validateUser(id, input.status )
    }

    fun createUser(newUser: CreateUserRequest) {
       userRepository.createUser(newUser.username, newUser.email, newUser.password)
    }
}