package project.smartlocker.http.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import project.smartlocker.http.utlis.Uris
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.http.MediaType

@RestController
@RequestMapping(Uris.API)
class HardwareController() {
    private val webClient = WebClient.create()

    fun newTrade(ip: String, in_use: Boolean, available:Boolean, door:String): String? {
        val url = "http://${ip}/status"

        val requestBody = mapOf("in_use" to in_use, "door" to door, "available" to available)

        val response = webClient.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String::class.java)
            .block() // block for simplicity here

        return response
    }

    fun getStatus(ip:String): String?{
        val url = "http://${ip}/status"

        val response = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return response
    }

}