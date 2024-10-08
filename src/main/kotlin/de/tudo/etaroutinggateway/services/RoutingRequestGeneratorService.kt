package de.tudo.etaroutinggateway.services

import de.tudo.etaroutinggateway.entities.VehicleType
import de.tudo.etaroutinggateway.entities.dtos.gaiax.MetadataDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RouteLocationDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoutingRequestGeneratorService(
    @Autowired
    private val kafkaService: KafkaService,
    @Autowired
    private val otpRouteMappingService: OtpRouteMappingService
) {

    fun generateRoutingRequest(numLocations: Int = 3,
                               vehicleType: VehicleType = VehicleType.CAR): RoutingRequestDto {
        return RoutingRequestDto(
            requestId = UUID.randomUUID().toString(),
            routeLocations = (0..numLocations).map {
                RouteLocationDto(
                    latitude = 52.45936 + Math.random() * (53.36039-52.45936), //49.434114 + Math.random() * (52.866945-49.434114),//51.1642292,
                    longitude = 8.36609 + Math.random() * (10.05249-8.36609), //7.29526 + Math.random() * (11.676014-7.29526),//10.4541194,
                    waitingTime = (Math.random() * 100.0).toInt()
                )
            }.toList(),
            metadata = MetadataDto(
                vehicleType = VehicleType.CAR
            )
        )
    }

    fun sendKafkaGeneratedRoutingRequest(routingRequest: RoutingRequestDto) {
        println("RoutingRequestGeneratorService: Sending routing request: $routingRequest")
        kafkaService.sendRoutingRequest(routingRequest)

    }

    fun generateAndSendKafkaRoutingRequest(): RoutingRequestDto {
        val routingRequest = generateRoutingRequest()
        sendKafkaGeneratedRoutingRequest(routingRequest)
        return routingRequest
    }

    fun generateAndSendOtpRoutingRequest(): RoutingResponseDto {
        return otpRouteMappingService.getRouteForRequest(generateRoutingRequest())
    }
}