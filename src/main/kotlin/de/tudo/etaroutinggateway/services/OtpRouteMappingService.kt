package de.tudo.etaroutinggateway.services

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
        this.httpRouteRequest()
        return RoutingResponseDto()
    }

    fun handleRouteRequestAndSendResponse(routingRequestDto: RoutingRequestDto?) {
        kafkaService.sendRoutingResponse(getRouteForRequest(routingRequestDto!!))
    }

    fun httpRouteRequest() {
        val requestUrl = "/otp/routers/default/plan?fromPlace=52.40912125231122,9.514160156250002&toPlace=52.98833725339543,9.937133789062502&time=4:12pm&date=01-23-2024&mode=CAR&arriveBy=false&wheelchair=false&showIntermediateStops=true&locale=en"

        print(restTemplate.getForObject(otpServerAddress + requestUrl, String::class.java))
        val routingResponse: OtpResponseDto? = restTemplate.getForObject(otpServerAddress + requestUrl, OtpResponseDto::class.java)
        print(routingResponse)
    }
}