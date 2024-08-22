package de.tudo.etaroutinggateway.services

import de.tudo.etaroutinggateway.entities.VehicleType
import de.tudo.etaroutinggateway.entities.dtos.gaiax.*
import de.tudo.etaroutinggateway.entities.dtos.otp.OtpResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

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
        var responses = ArrayList<RoutingFeatureDto>()
        for (i in 0..routingRequest.routeLocations.size - 2) {
            responses.addAll(this.getGaiaXRouteForStartAndEnd(routingRequest.routeLocations[i],
                routingRequest.routeLocations[i + 1],
                routingRequest.metadata.vehicleType))
        }
        return RoutingResponseDto(features = responses,)
    }

    fun getGaiaXRouteForStartAndEnd(start: RouteLocationDto, end: RouteLocationDto, mode: VehicleType): List<RoutingFeatureDto> {
        val response = this.getRouteForStartEnd(start, end, mode)
        val routingResponseKafka = response.toGaiaxRoutingResponseDto()
        return routingResponseKafka.features
    }

    fun handleRouteRequestAndSendResponse(routingRequestDto: RoutingRequestDto?) {
        kafkaService.sendRoutingResponse(getRouteForRequest(routingRequestDto!!),
            routingRequestDto.requestId.toString()
        )
    }

    fun getRouteForStartEnd(start: RouteLocationDto, end: RouteLocationDto, mode: VehicleType): OtpResponseDto {
        val requestUrl = "/otp/routers/default/plan?fromPlace=${start.latitude},${start.longitude}" +
                "&toPlace=${end.latitude},${end.longitude}&time=4:12pm&date=01-23-2024&mode=$mode&arriveBy=false" +
                "&wheelchair=false&showIntermediateStops=true&locale=en"
        val routingResponse: OtpResponseDto? = restTemplate.getForObject(otpServerAddress + requestUrl, OtpResponseDto::class.java)
        return routingResponse!!
    }
}