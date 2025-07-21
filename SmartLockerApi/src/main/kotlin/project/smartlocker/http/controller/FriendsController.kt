package project.smartlocker.http.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.domain.user.User
import project.smartlocker.http.models.friends.input.CreateFriendRequest
import project.smartlocker.http.models.friends.input.UpdateFriendRequest
import project.smartlocker.http.models.friends.output.AdminFriendDTO
import project.smartlocker.http.models.friends.output.FriendDTO
import project.smartlocker.http.models.user.output.UserDTO
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.FriendsService


@RestController
@RequestMapping(Uris.API)
class FriendsController(
    private val friendsService: FriendsService
) {
    // admin
    @GetMapping(Uris.Friends.GET_FRIENDS)
    fun getAllFriends(
        @PathVariable id: Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }

        val friends = friendsService.getAllFriends(id)
        return ResponseEntity.ok(friends)
    }

    @GetMapping(Uris.Friends.GET_FRIEND)
    fun getFriend(
        @PathVariable id: Int,
        @PathVariable friendId:Int,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        if (user.role != RoleEnum.ADMIN.toString()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, you don’t have permission for this page.")
        }
        val friend = friendsService.getFriend(id, friendId)
        return ResponseEntity.ok(friend)
    }
/*
    // global
    @GetMapping(Uris.Friends.GET_FRIENDS_INFO)
    fun getAllFriendsInfo(@PathVariable id: Int): ResponseEntity<List<FriendDTO>> {
        val friends = friendsService.getAllFriendsInfo(id)
        return ResponseEntity.ok(friends)
    }

    @PostMapping(Uris.Friends.CREATE_FRIEND)
    fun createFriend(@PathVariable id: Int, @RequestBody input: CreateFriendRequest): ResponseEntity<Void> {
        friendsService.createFriend(id, input)
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


 */

    // app
    @GetMapping(Uris.Friends.GET_USER_FRIENDS)
    fun getFriends(
        request: HttpServletRequest
    ): ResponseEntity<List<FriendDTO>> {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        val friends = friendsService.getFriends(user.id)
        return ResponseEntity.ok(friends)
    }

    @PostMapping(Uris.Friends.ADD_FRIEND)
    fun addFriend(
        request: HttpServletRequest,
        @RequestBody input: CreateFriendRequest
    ): ResponseEntity<Void> {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        friendsService.addFriend(user.id, input)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping(Uris.Friends.EDIT_FRIEND)
    fun editFriend(
        request: HttpServletRequest,
        @PathVariable friendId: Int,
        @RequestBody input: UpdateFriendRequest
    ): ResponseEntity<Void> {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        friendsService.editFriends(user.id, friendId, input)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping(Uris.Friends.REMOVE_FRIEND)
    fun removeFriend(
        request: HttpServletRequest,
        @PathVariable friendId:Int
    ): ResponseEntity<Void> {
        val user = request.getAttribute("authenticatedUser") as? User
            ?: throw RuntimeException("No user in context")

        friendsService.removeFriends(user.id, friendId)
        return ResponseEntity.ok().build()
    }
}