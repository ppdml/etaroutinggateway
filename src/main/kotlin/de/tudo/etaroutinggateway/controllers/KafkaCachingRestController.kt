package de.tudo.etaroutinggateway.controllers

import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingResponseDto
import de.tudo.etaroutinggateway.services.RouteCachingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/caching")
class KafkaCachingRestController {
    @GetMapping("/requests")
    fun getCachedRequests(): List<RoutingRequestDto> {
        return RouteCachingService.getInstance().getRequests()
    }

    @GetMapping("/responses")
    fun getCachedResponses(): List<RoutingResponseDto> {
        return RouteCachingService.getInstance().getResponses()
    }
}