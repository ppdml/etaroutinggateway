package de.tudo.etaroutinggateway.services

import de.tudo.etaroutinggateway.entities.VehicleType
import de.tudo.etaroutinggateway.entities.dtos.gaiax.*
import de.tudo.etaroutinggateway.entities.dtos.otp.OtpResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        val responses = ArrayList<RoutingFeatureDto>()
        for (i in 0..routingRequest.routeLocations.size - 2) {
            responses.addAll(
                this.getGaiaXRouteForStartAndEnd(
                    routingRequest.routeLocations[i],
                    routingRequest.routeLocations[i + 1],
                    routingRequest.metadata.vehicleType
                )
            )
        }
        val routingResponseDto = RoutingResponseDto(features = responses)
        RouteCachingService.getInstance().addRequest(routingRequest)
        RouteCachingService.getInstance().addResponse(routingResponseDto)
        return routingResponseDto
    }

    fun getGaiaXRouteForStartAndEnd(
        start: RouteLocationDto,
        end: RouteLocationDto,
        mode: VehicleType
    ): List<RoutingFeatureDto> {
        val response = this.getRouteForStartEnd(start, end, mode)
        val routingResponseKafka = response.toGaiaxRoutingResponseDto()

        var updatedList: List<List<Double>> = listOf()
        if (routingResponseKafka.features.isNotEmpty()) {
            updatedList = routingResponseKafka.features[0].geometry.coordinates
        }
        if (routingResponseKafka.features.isEmpty() ||
            routingResponseKafka.features[0].geometry.coordinates.isEmpty() ||
            (52.310731857708134 < start.latitude && start.latitude < 52.317208653922705 &&
                    8.003401925933604 < start.longitude && start.longitude < 8.012513791827066)
        ) {
            updatedList = listOf(listOf(start.longitude, start.latitude)) + updatedList
        }

        if (routingResponseKafka.features.isEmpty() ||
            routingResponseKafka.features[0].geometry.coordinates.isEmpty() ||
            (52.310731857708134 < end.latitude && end.latitude < 52.317208653922705 &&
                    8.003401925933604 < end.longitude && end.longitude < 8.012513791827066)
        ) {
            updatedList = updatedList + listOf(listOf(end.longitude, end.latitude))
        }

        if (routingResponseKafka.features.isEmpty()) {
            routingResponseKafka.features = listOf(
                RoutingFeatureDto(
                    geometry = RoutingGeometryDto(coordinates = updatedList),
                )
            )
        } else {
            routingResponseKafka.features[0].geometry.coordinates = updatedList
        }
        return routingResponseKafka.features
    }

    fun handleRouteRequestAndSendResponse(routingRequestDto: RoutingRequestDto?) {
        kafkaService.sendRoutingResponse(
            getRouteForRequest(routingRequestDto!!),
            routingRequestDto.requestId.toString()
        )
    }

    fun getRouteForStartEnd(start: RouteLocationDto, end: RouteLocationDto, mode: VehicleType): OtpResponseDto {
        val current = LocalDateTime.now()
        val timeFormat = DateTimeFormatter.ofPattern("h:mma")
        val dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        val requestUrl = "/otp/routers/default/plan?fromPlace=${start.latitude},${start.longitude}" +
                "&toPlace=${end.latitude},${end.longitude}&time=${current.format(timeFormat)}&date=${
                    current.format(
                        dateFormat
                    )
                }&mode=${mode.type}&arriveBy=false" +
                "&wheelchair=false&showIntermediateStops=true&locale=en"
        val routingResponse: OtpResponseDto =
            restTemplate.getForObject(otpServerAddress + requestUrl, OtpResponseDto::class.java)
                ?: throw IllegalStateException("Could not find OtpResponseDto")
        return routingResponse
    }
}