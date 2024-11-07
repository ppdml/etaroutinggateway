package de.tudo.etaroutinggateway.services

import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingResponseDto


class RouteCachingService private constructor() {
    private var routingRequests = emptyList<RoutingRequestDto>()
    private var routingResponses = emptyList<RoutingResponseDto>()

    companion object {

        @Volatile
        private var instance: RouteCachingService? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: RouteCachingService().also { instance = it }
            }
    }

    fun addRequest(request: RoutingRequestDto) {
        routingRequests = listOf(request) + routingRequests
        routingRequests = routingRequests.take(30)
    }

    fun addResponse(response: RoutingResponseDto) {
        routingResponses = listOf(response) + routingResponses
        routingResponses = routingResponses.take(30)
    }

    fun getRequests(): List<RoutingRequestDto> {
        return routingRequests
    }

    fun getResponses(): List<RoutingResponseDto> {
        return routingResponses
    }
}