package project.smartlocker.http.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.http.models.friends.CreateFriendRequest
import project.smartlocker.http.models.friends.FriendDTO
import project.smartlocker.http.models.friends.UpdateFriendRequest
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.FriendsService


@RestController
@RequestMapping(Uris.HOME)
class FriendsController(
    private val friendsService: FriendsService
) {
    @GetMapping(Uris.Friends.GET_FRIENDS)
    fun getAllFriends(@PathVariable id: Int): ResponseEntity<List<FriendDTO>> {
        val friends = friendsService.getAllFriends(id)
        return ResponseEntity.ok(friends)
    }

    @GetMapping(Uris.Friends.GET_FRIEND)
    fun getFriend(@PathVariable id: Int, @PathVariable friendId:Int): ResponseEntity<FriendDTO> {
        val friend = friendsService.getFriend(id, friendId)
        return ResponseEntity.ok(friend)
    }

    @PostMapping(Uris.Friends.ADD_FRIEND)
    fun createFriends(@PathVariable id: Int, @RequestBody input: CreateFriendRequest): ResponseEntity<Void> {
        friendsService.createFriends(id, input)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping(Uris.Friends.UPDATE_FRIEND)
    fun updateFriends(@PathVariable id:Int, @PathVariable friendId:Int, @RequestBody input: UpdateFriendRequest): ResponseEntity<Void> {
        friendsService.updateFriends(id, friendId, input)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping(Uris.Friends.DELETE_FRIEND)
    fun deleteFriends(@PathVariable id:Int, @PathVariable friendId:Int): ResponseEntity<Void> {
        friendsService.deleteFriends(id, friendId)
        return ResponseEntity.noContent().build()
    }
}



