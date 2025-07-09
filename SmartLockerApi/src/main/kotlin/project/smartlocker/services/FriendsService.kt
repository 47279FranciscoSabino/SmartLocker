package project.smartlocker.services

import org.springframework.stereotype.Service
import project.smartlocker.http.models.friends.input.CreateFriendRequest
import project.smartlocker.http.models.friends.input.UpdateFriendRequest
import project.smartlocker.http.models.friends.output.AdminFriendDTO
import project.smartlocker.http.models.friends.output.FriendDTO
import project.smartlocker.repository.FriendsRepository


@Service
class FriendsService(
    private val friendsRepository: FriendsRepository
) {
    // admin
    fun getAllFriends(user :Int): List<AdminFriendDTO> {
        return friendsRepository.getAllFriends(user)
    }

    fun getFriend(user :Int, friendId:Int): AdminFriendDTO? {
        return friendsRepository.getFriend(user, friendId)
    }

    // global
    fun getAllFriendsInfo(id :Int): List<FriendDTO> {
        return friendsRepository.getAllFriendsInfo(id)
    }

    fun addFriend(id: Int, input: CreateFriendRequest) {
        friendsRepository.addFriend(id, input.friend)
    }

    fun updateFriends(id: Int, friend:Int, input: UpdateFriendRequest) {
        friendsRepository.updateFriend(id, friend, input.status)
    }

    fun deleteFriends(id: Int, friend:Int) {
        friendsRepository.deleteFriends(id, friend)
    }
}