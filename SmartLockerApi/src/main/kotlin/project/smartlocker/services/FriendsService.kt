package project.smartlocker.services

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import project.smartlocker.domain.friends.FriendsEnum
import project.smartlocker.domain.locker.LockerEnum
import project.smartlocker.http.models.friends.CreateFriendRequest
import project.smartlocker.http.models.friends.FriendDTO
import project.smartlocker.http.models.friends.UpdateFriendRequest
import project.smartlocker.http.models.locker.LockerDTO
import project.smartlocker.http.models.locker.UpdateLockerRequest
import project.smartlocker.repository.FriendsRepository


@Service
class FriendsService(
    private val friendsRepository: FriendsRepository
) {
    fun getAllFriends(user :Int): List<FriendDTO> {
        val friends = friendsRepository.getAllFriends(user)
        val outputs = friends.map {
            val status = friendsRepository.getFriendsStatus(it.user, it.friend )
            FriendDTO(it.user, it.friend, it.date,status.status.toString())
        }
        return outputs
    }

    fun getFriend(user :Int, friendId:Int): FriendDTO? {
        val friend = friendsRepository.getFriend(user, friendId)
        friend?.let {
            val status = friendsRepository.getFriendsStatus(it.user, it.friend)
            return FriendDTO(it.user, it.friend, it.date, status.status.toString())
        }
        return null
    }

    fun createFriends(id: Int, input: CreateFriendRequest) {
        friendsRepository.createFriends(id, input.friend, input.date)
        val status = FriendsEnum.PENDING.toString()
        friendsRepository.createFriendsStatus(id, input.friend, status)
    }

    fun updateFriends(id: Int, friend:Int, input: UpdateFriendRequest) {
        friendsRepository.updateFriends(id, friend, input.date)
        friendsRepository.updateFriendsStatus(id, friend, input.status)
    }

    fun deleteFriends(id: Int, friend:Int) {
        friendsRepository.deleteFriendsStatus(id, friend)
        friendsRepository.deleteFriends(id, friend)
    }
}