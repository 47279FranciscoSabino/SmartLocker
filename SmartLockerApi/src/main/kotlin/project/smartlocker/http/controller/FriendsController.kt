package project.smartlocker.http.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.domain.user.RoleEnum
import project.smartlocker.http.models.friends.input.CreateFriendRequest
import project.smartlocker.http.models.friends.input.UpdateFriendRequest
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
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(friends)
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
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(friend)
    }

    // app
    @GetMapping(Uris.Friends.GET_USER_FRIENDS)
    fun getFriends(
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        val friends = friendsService.getFriends(user.id)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(friends)
    }

    @PostMapping(Uris.Friends.ADD_FRIEND)
    fun addFriend(
        request: HttpServletRequest,
        @RequestBody input: CreateFriendRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        friendsService.addFriend(user.id, input.friend)
        return  ResponseEntity.status(HttpStatus.CREATED)
            .body("Friend added.")
    }

    @PutMapping(Uris.Friends.EDIT_FRIEND)
    fun editFriend(
        request: HttpServletRequest,
        @PathVariable friendId: Int,
        @RequestBody input: UpdateFriendRequest
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        friendsService.editFriends(user.id, friendId, input.status)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Friend status updated.")
    }

    @DeleteMapping(Uris.Friends.REMOVE_FRIEND)
    fun removeFriend(
        request: HttpServletRequest,
        @PathVariable friendId:Int
    ): ResponseEntity<Any> {
        val user = request.getAttribute("authenticatedUser") as? UserDTO
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized: Please log in to access this resource.")

        friendsService.removeFriends(user.id, friendId)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Friend removed.")
    }
}