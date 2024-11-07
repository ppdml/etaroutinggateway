package de.tudo.etaroutinggateway.controllers

import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.services.KafkaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/routingRequest")
class RoutingRequestController(
    @Autowired
    private val kafkaService: KafkaService,
) {
    @PostMapping("/")
    fun postRequest(@RequestBody request: RoutingRequestDto) {

        kafkaService.sendRoutingRequest(request)
    }
}