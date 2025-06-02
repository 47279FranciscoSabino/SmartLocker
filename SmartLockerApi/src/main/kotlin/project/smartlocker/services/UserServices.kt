package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.domain.user.UserEnum
import project.smartlocker.http.models.user.CreateUserRequest
import project.smartlocker.http.models.user.UpdateUserRequest
import project.smartlocker.http.models.user.UserDTO
import project.smartlocker.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<UserDTO> {
        val users = userRepository.getAllUsers()
        val outputs = users.map {
            val status = userRepository.getUserStatus(it.id)
            UserDTO(it.id, it.username, it.email, it.password, status?.status.toString())
        }
        return outputs
    }

    fun getUserById(id: Int): UserDTO? {
        val user = userRepository.getUserById(id)
        return if (user != null) {
            val status = userRepository.getUserStatus(id)
            UserDTO(user.id, user.username, user.email, user.password, status?.status.toString())
        }
        else{
            UserDTO(0, "e", "r", "r", "o")
        }
    }

    fun createUser(newUser: CreateUserRequest) {
        val userId = userRepository.createUser(newUser.username, newUser.email, newUser.password)
        userRepository.createUserStatus(userId, UserEnum.NOT_VERIFIED.toString())
    }

    fun updateUser(id: Int, user: UpdateUserRequest) {
        userRepository.updateUser(id, user.username, user.email, user.password)
        userRepository.updateUserStatus(id, user.status)
    }

/*
    fun deleteUser(id: Int){
        val userId = userRepository.deleteUserStatus(id)
        userRepository.deleteUser(userId)
    }
*/
}


