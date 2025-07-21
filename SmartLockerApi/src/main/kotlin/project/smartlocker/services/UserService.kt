package project.smartlocker.services

import project.smartlocker.http.models.user.output.UserInfoDTO
import org.springframework.stereotype.Service
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.domain.user.User
import project.smartlocker.domain.user.UserEnum
import project.smartlocker.http.models.user.input.*
import project.smartlocker.http.models.user.output.AdminUserDTO
import project.smartlocker.http.models.user.output.AdminUserStatusDTO
import project.smartlocker.http.models.user.output.TokenDTO
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.repository.UserRepository
import java.util.*

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
        val user = userRepository.getUserById(id) ?: throw Exception("User not found")
        if(user != null){
            userRepository.deleteUser(id)
        }
    }

    fun editRole(id: Int, update: UpdateUserRoleRequest){
        userRepository.getUserById(id) ?: throw Exception("User not found")
        val role = try {
            RoleEnum.valueOf(update.role.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid role: ${update.role}")
        }
        userRepository.editRole(id, role.name)
    }

    // global
    fun getUserByUsername(name:String):UserDTO?{                        //
        return userRepository.getUserByUsername(name)
    }

    fun editUserStatus(id: Int, input: UpdateUserStatusRequest){
        val user = userRepository.getUserById(id) ?: throw Exception("User not found")
        val status = try {
            UserEnum.valueOf(input.status.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid role: ${input.status}")
        }

        when (user.status) {
            UserEnum.NOT_VERIFIED.toString() -> if (status != UserEnum.VERIFIED) {
                throw IllegalArgumentException("Can only transition from NOT_VERIFIED to VERIFIED")
            }
            UserEnum.VERIFIED.toString() -> if (status != UserEnum.SUSPENDED) {
                throw IllegalArgumentException("Can only transition from VERIFIED to SUSPENDED")
            }
            UserEnum.SUSPENDED.toString() -> if (status != UserEnum.VERIFIED) {
                throw IllegalArgumentException("Can only transition from SUSPENDED to VERIFIED")
            }
        }
        userRepository.editUserStatus(id, input.status)
    }



//--------------------------------------------------------------------------------------------------------

    fun login(input: LoginRequest): TokenDTO {
        val user = userRepository.getUserByEmail(input.email) ?: throw Exception("Invalid email or password")

        if (input.password != user.password) {
            throw Exception("Invalid email or password")
        }

        val newToken = UUID.randomUUID()
        userRepository.updateUserToken(user.id, newToken)

        return TokenDTO(newToken)
    }

    fun logout(user: User) {
         userRepository.deleteToken(user.id)
    }

    fun createUser(input: CreateUserRequest) {
        val user = userRepository.getUserByEmail(input.email)
        if (user != null) {
            throw Exception("Email already registered")
        }
        userRepository.createUser(input.username, input.email, input.password)
    }

    fun getUserById(id: Int): UserDTO? {
        return userRepository.getUserById(id)
    }

    fun updateUser(id: Int, input: UpdateUserRequest) {
        val user = userRepository.getUser(id) ?: throw Exception("User not found")

        val newEmail = input.email ?: user.email
        val newPassword = input.password ?: user.password

        userRepository.updateUser(id, newEmail, newPassword)
    }

    fun getUserInfo(id: Int): UserInfoDTO? {
        return userRepository.getUserInfo(id)
    }
}