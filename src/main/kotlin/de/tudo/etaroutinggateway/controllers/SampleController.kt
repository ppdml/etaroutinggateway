package de.tudo.etaroutinggateway.controllers

import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingResponseDto
import de.tudo.etaroutinggateway.services.RoutingRequestGeneratorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleController(
    @Autowired
    val routingRequestGeneratorService: RoutingRequestGeneratorService
) {

    @GetMapping("/generate")
    fun generateRandomRequest(): RoutingRequestDto {
        return routingRequestGeneratorService.generateAndSendKafkaRoutingRequest()
    }

    @GetMapping("/otpsamplerequest")
    fun sampleOtpRoutingRequest(): RoutingResponseDto {
        return routingRequestGeneratorService.generateAndSendOtpRoutingRequest()
    }
}