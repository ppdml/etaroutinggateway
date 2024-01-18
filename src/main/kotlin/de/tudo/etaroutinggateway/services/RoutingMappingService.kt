package de.tudo.etaroutinggateway.services

import de.tudo.etaroutinggateway.entities.dtos.RoutingRequestDto
import de.tudo.etaroutinggateway.entities.dtos.RoutingResponseDto
import org.springframework.stereotype.Service

@Service
class RoutingMappingService(
    private val kafkaService: KafkaService
) {
    fun getRouteForRequest(routingRequest: RoutingRequestDto): RoutingResponseDto {
        return RoutingResponseDto()
    }

    fun handleRouteRequestAndSendResponse(routingRequestDto: RoutingRequestDto?) {
        kafkaService.sendRoutingResponse(getRouteForRequest(routingRequestDto!!))
    }
}