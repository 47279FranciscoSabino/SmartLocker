package project.smartlocker.http.controller

import com.example.smartlockerandroid.data.model.hash.output.QrScanDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.smartlocker.http.models.friends.output.AdminFriendDTO
import project.smartlocker.http.models.hash.input.CreateHashRequest
import project.smartlocker.http.models.locker.input.CreateLockerRequest
import project.smartlocker.http.utlis.Uris
import project.smartlocker.services.HashService

@RestController
@RequestMapping(Uris.HOME)
class HashController(
    private val hashService: HashService
) {
    //admin
    @PostMapping(Uris.Hash.CREATE_HASH)
    fun insertHash(@RequestBody insert: CreateHashRequest){
        hashService.createQrCode(insert)
    }

    //global
    @GetMapping(Uris.Hash.GET_HASH)
    fun getHash(@PathVariable hash: String): ResponseEntity<QrScanDTO> {
        val h = hashService.getHash(hash)
        return ResponseEntity.ok(h)
    }
}

