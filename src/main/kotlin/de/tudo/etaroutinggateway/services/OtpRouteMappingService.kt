package de.tudo.etaroutinggateway.services

import de.tudo.etaroutinggateway.entities.VehicleType
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RouteLocationDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingFeatureDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingResponseDto
import de.tudo.etaroutinggateway.entities.dtos.otp.OtpResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
class OtpRouteMappingService(
    @Autowired
    private val kafkaService: KafkaService,
    @Autowired
    private val restTemplate: RestTemplate
) {

    @Value("\${otp.server.address}")
    private val otpServerAddress: String? = null

    fun getRouteForRequest(routingRequest: RoutingRequestDto): RoutingResponseDto {
        if (routingRequest.routeLocations.size < 2) throw Exception("Two locations are required for routing")
        var responses = ArrayList<RoutingResponseDto>()
        for (i in 0..routingRequest.routeLocations.size - 2) {
            responses.add(this.getGaiaXRouteForStartAndEnd(routingRequest.routeLocations[i],
                routingRequest.routeLocations[i + 1],
                routingRequest.metadata.vehicleType))
        }
        val features = ArrayList<RoutingFeatureDto>()
        for (response in responses) {
            features.addAll(response.features)
        }
        return RoutingResponseDto(features = features)
    }

    fun getGaiaXRouteForStartAndEnd(start: RouteLocationDto, end: RouteLocationDto, mode: VehicleType): RoutingResponseDto {
        val response = this.getRouteForStartEnd(start, end, mode)
        val routingResponseKafka = response.toGaiaxRoutingResponseDto()
        return routingResponseKafka
    }

    fun handleRouteRequestAndSendResponse(routingRequestDto: RoutingRequestDto?) {
        kafkaService.sendRoutingResponse(getRouteForRequest(routingRequestDto!!))
    }

    fun getRouteForStartEnd(start: RouteLocationDto, end: RouteLocationDto, mode: VehicleType): OtpResponseDto {
        val requestUrl = "/otp/routers/default/plan?fromPlace=${start.latitude},${start.longitude}" +
                "&toPlace=${end.latitude},${end.longitude}&time=4:12pm&date=01-23-2024&mode=$mode&arriveBy=false" +
                "&wheelchair=false&showIntermediateStops=true&locale=en"
        val routingResponse: OtpResponseDto? = restTemplate.getForObject(otpServerAddress + requestUrl, OtpResponseDto::class.java)
        return routingResponse!!
    }
}