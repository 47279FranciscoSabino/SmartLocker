package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.domain.friends.FriendsEnum
import project.smartlocker.http.models.friends.output.AdminFriendDTO
import project.smartlocker.http.models.friends.output.FriendDTO
import project.smartlocker.repository.FriendsRepository
import project.smartlocker.repository.UserRepository

@Service
class FriendsService(
    private val friendsRepository: FriendsRepository,
    private val userRepository: UserRepository
) {
    // admin
    fun getAllFriends(user :Int): List<AdminFriendDTO> {
        userRepository.getUserById(user) ?: throw Exception("User $user not found")
        return friendsRepository.getAllFriends(user)
    }

    fun getFriend(user :Int, friendId:Int): AdminFriendDTO? {
        userRepository.getUserById(user) ?: throw Exception("User $user not found")
        userRepository.getUserById(friendId) ?: throw Exception("User $friendId not found")
        return friendsRepository.getFriend(user, friendId)
    }

    // app
    fun getFriends(id :Int): List<FriendDTO> {
        return friendsRepository.getFriends(id)
    }

    fun addFriend(id: Int, friend: Int) {
        require(id != friend) { "You cannot add yourself as a friend." }

        if (userRepository.getUserById(friend)==null) {
            throw IllegalArgumentException("User with ID $friend does not exist.")
        }

        val existingRelation = friendsRepository.getFriend(id, friend)
        if (existingRelation != null) {
            throw IllegalStateException("Friend relationship already exists or is pending.")
        }

        friendsRepository.addFriend(id, friend)
    }

    fun editFriends(id: Int, friend:Int, status: String) {
        val newStatus = try {
            FriendsEnum.valueOf(status.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid status: $status")
        }
        val existingRelation = friendsRepository.getFriend(id, friend)
            ?: throw IllegalStateException("No friend relation found.")

        if (FriendsEnum.valueOf(existingRelation.status) == FriendsEnum.ACCEPTED
                && newStatus == FriendsEnum.PENDING) {
            throw IllegalStateException("Invalid change")
        }

        friendsRepository.editFriend(id, friend, status)
    }

    fun removeFriends(id: Int, friend:Int) {
        friendsRepository.getFriend(id, friend)
            ?: throw IllegalStateException("Friend relation does not exist.")

        friendsRepository.removeFriend(id, friend)
    }
}