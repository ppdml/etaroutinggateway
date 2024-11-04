package de.tudo.etaroutinggateway.controllers

import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingResponseDto
import de.tudo.etaroutinggateway.services.OtpRouteMappingService
import de.tudo.etaroutinggateway.services.RoutingRequestGeneratorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sample")
class SampleController(
    @Autowired
    val routingRequestGeneratorService: RoutingRequestGeneratorService,
    @Autowired
    val otpRouteMappingService: OtpRouteMappingService
) {

    @GetMapping("/generate")
    fun generateRandomRequest(): RoutingRequestDto {
        return routingRequestGeneratorService.generateAndSendKafkaRoutingRequest()
    }

    @PostMapping("/routing")
    fun getResponseForRequest(@RequestBody request: RoutingRequestDto): RoutingResponseDto {
        return otpRouteMappingService.getRouteForRequest(request)
    }

    @GetMapping("/otpsamplerequest")
    fun sampleOtpRoutingRequest(): RoutingResponseDto {
        return routingRequestGeneratorService.generateAndSendOtpRoutingRequest()
    }
}