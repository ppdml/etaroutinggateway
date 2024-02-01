package de.tudo.etaroutinggateway.entities.dtos.otp

import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingResponseDto

data class OtpResponseDto(
    val requestParameters: OtpRequestParametersDto,
    val plan: OtpPlanDto?,
){
    fun toGaiaxRoutingResponseDto(): RoutingResponseDto {
        val routingResponse: RoutingResponseDto = RoutingResponseDto()
        routingResponse.features = plan?.itineraries?.map { itinerary ->
            itinerary.toGaiaxRoutingFeatureDto()
        }?.toList() ?: listOf()
        return routingResponse
    }
}