package project.smartlocker.services

import org.springframework.security.crypto.password.PasswordEncoder
import project.smartlocker.http.models.user.output.UserInfoDTO
import org.springframework.stereotype.Service
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.domain.user.UserEnum
import project.smartlocker.http.models.user.output.AdminUserDTO
import project.smartlocker.http.models.user.output.AdminUserStatusDTO
import project.smartlocker.http.models.user.output.TokenDTO
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.repository.UserRepository
import java.util.*

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
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

    fun editRole(id: Int, role: String){
        userRepository.getUserById(id) ?: throw Exception("User not found")
        val newRole = try {
            RoleEnum.valueOf(role.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid role: $role")
        }
        userRepository.editRole(id, newRole.name)
    }

    // global
    fun getUserByUsername(name:String):UserDTO?{                        //
        return userRepository.getUserByUsername(name)
    }

    fun editUserStatus(id: Int, status: String){
        val user = userRepository.getUserById(id) ?: throw Exception("User not found")
        val newStatus = try {
            UserEnum.valueOf(status.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid role: $status")
        }

        when (user.status) {
            UserEnum.NOT_VERIFIED.toString() -> if (newStatus != UserEnum.VERIFIED) {
                throw IllegalArgumentException("Can only transition from NOT_VERIFIED to VERIFIED")
            }
            UserEnum.VERIFIED.toString() -> if (newStatus != UserEnum.SUSPENDED) {
                throw IllegalArgumentException("Can only transition from VERIFIED to SUSPENDED")
            }
            UserEnum.SUSPENDED.toString() -> if (newStatus != UserEnum.VERIFIED) {
                throw IllegalArgumentException("Can only transition from SUSPENDED to VERIFIED")
            }
        }
        userRepository.editUserStatus(id, status)
    }



//--------------------------------------------------------------------------------------------------------

    fun login(email: String, password:String): TokenDTO {
        val user = userRepository.getUserByEmail(email) ?: throw Exception("Invalid email or password")

        val isValid = passwordEncoder.matches(password, user.password)

        if (!isValid) {
            throw Exception("Invalid email or password")
        }

        val newToken = UUID.randomUUID()
        userRepository.updateUserToken(user.id, newToken)

        return TokenDTO(newToken)
    }

    fun logout(userId: Int) {
         userRepository.deleteToken(userId)
    }

    fun createUser(username: String, email:String, password:String) {
        if(password.length < 8) throw Exception("Password must be more than 8 characters")
        val hashedPassword = passwordEncoder.encode(password)
        val user = userRepository.getUserByEmail(email)
        if (user != null) {
            throw Exception("Email already registered")
        }
        userRepository.createUser(username, email, hashedPassword)
    }

    fun getUserById(id: Int): UserDTO? {
        return userRepository.getUserById(id)
    }

    fun updateUser(id: Int, email: String?, password: String?) {
        val user = userRepository.getUser(id) ?: throw Exception("User not found")

        val newEmail = email ?: user.email
        val newPassword = password ?: user.password

        userRepository.updateUser(id, newEmail, newPassword)
    }

    fun getUserInfo(id: Int): UserInfoDTO? {
        return userRepository.getUserInfo(id)
    }
}