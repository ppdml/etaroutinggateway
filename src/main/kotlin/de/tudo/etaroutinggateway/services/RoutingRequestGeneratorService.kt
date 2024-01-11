package de.tudo.etaroutinggateway.services

import de.tudo.etaroutinggateway.entities.RoutingRequest
import de.tudo.etaroutinggateway.entities.VehicleType
import de.tudo.etaroutinggateway.entities.dtos.MetadataDto
import de.tudo.etaroutinggateway.entities.dtos.RouteLocationDto
import de.tudo.etaroutinggateway.entities.dtos.RoutingRequestDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoutingRequestGeneratorService {

    fun generateRoutingRequest(numLocations: Int = 2,
                               vehicleType: VehicleType = VehicleType.CAR): RoutingRequestDto {
        return RoutingRequestDto(
            requestId = UUID.randomUUID(),
            routeLocations = (0..numLocations).map {
                RouteLocationDto(
                    latitude = 49.434114 + Math.random() * (52.866945-49.434114),//51.1642292,
                    longitude = 7.29526 + Math.random() * (11.676014-7.29526),//10.4541194,
                    waitingTime = (Math.random() * 100.0).toInt()
                )
            }.toList(),
            metadata = MetadataDto(
                vehicleType = VehicleType.CAR
            )
        )
    }

    fun sendGeneratedRoutingRequest(routingRequest: RoutingRequestDto) {
        println("RoutingRequestGeneratorService: Sending routing request: $routingRequest")
    }

    fun generateAndSendRoutingRequest(): RoutingRequestDto {
        val routingRequest = generateRoutingRequest()
        sendGeneratedRoutingRequest(routingRequest)
        return routingRequest
    }
}