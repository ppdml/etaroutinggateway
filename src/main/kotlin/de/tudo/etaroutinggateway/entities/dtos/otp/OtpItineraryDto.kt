package de.tudo.etaroutinggateway.entities.dtos.otp

import com.fasterxml.jackson.annotation.JsonFormat
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingFeatureDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingGeometryDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingPropertiesDto
import java.util.*

data class OtpItineraryDto(
    val duration: Long,
    @JsonFormat(without = [JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS])
    val startTime: Date,
    @JsonFormat(without = [JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS])
    val endTime: Date,
    val walkTime: Long,
    val transitTime: Long,
    val waitingTime: Long,
    val walkDistance: Double,
    val walkLimitExceeded: Boolean,
    val generalizedCost: Long,
    val elevationCost: Long,
    val elevationGained: Long,
    val transfers: Long,
    //val fare: OtpFareDto,
    val legs: List<OtpLegDto>,
    val tooSloped: Boolean,
    val arrivedAtDestinationWithRentedBicycle: Boolean,
) {
    fun toGaiaxRoutingFeatureDto(): RoutingFeatureDto {
        val routingFeature = RoutingFeatureDto(geometry = legs[0].toGaiaxRoutingGeometryDto(),
            properties = RoutingPropertiesDto(startTime = startTime, endTime = endTime, duration = duration))
        return routingFeature
    }

}
