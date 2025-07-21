package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.domain.friends.FriendsEnum
import project.smartlocker.http.models.friends.input.CreateFriendRequest
import project.smartlocker.http.models.friends.input.UpdateFriendRequest
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
/*
    // global
    fun getAllFriendsInfo(id :Int): List<FriendDTO> {
        return friendsRepository.getAllFriendsInfo(id)
    }

    fun createFriend(id: Int, input: CreateFriendRequest) {
        friendsRepository.createFriend(id, input.friend)
    }

    fun updateFriends(id: Int, friend:Int, input: UpdateFriendRequest) {
        friendsRepository.updateFriend(id, friend, input.status)
    }

    fun deleteFriends(id: Int, friend:Int) {
        friendsRepository.deleteFriends(id, friend)
    }

 */
    // app
    fun getFriends(id :Int): List<FriendDTO> {
        return friendsRepository.getFriends(id)
    }

    fun addFriend(id: Int, input: CreateFriendRequest) {
        val friend = input.friend
        require(id != friend) { "You cannot add yourself as a friend." }

        if (userRepository.getUserById(friend)==null) {
            throw IllegalArgumentException("User with ID $friend does not exist.")
        }

        val existingRelation = friendsRepository.getFriend(id, friend)
        if (existingRelation != null) {
            throw IllegalStateException("Friend relationship already exists or is pending.")
        }

        friendsRepository.addFriend(id, input.friend)
    }

    fun editFriends(id: Int, friend:Int, input: UpdateFriendRequest) {
        val status = try {
            FriendsEnum.valueOf(input.status.uppercase())
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid status: ${input.status}")
        }
        val existingRelation = friendsRepository.getFriend(id, friend)
            ?: throw IllegalStateException("No friend relation found.")

        if (FriendsEnum.valueOf(existingRelation.status) == FriendsEnum.ACCEPTED
                && status == FriendsEnum.PENDING) {
            throw IllegalStateException("Invalid change")
        }

        friendsRepository.editFriend(id, friend, input.status)
    }

    fun removeFriends(id: Int, friend:Int) {
        friendsRepository.getFriend(id, friend)
            ?: throw IllegalStateException("Friend relation does not exist.")

        friendsRepository.removeFriend(id, friend)
    }
}