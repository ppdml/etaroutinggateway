package de.tudo.etaroutinggateway.entities.dtos.otp

import de.tudo.etaroutinggateway.entities.VehicleType
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingGeometryDto

data class OtpLegDto(
    val startTime: Long,
    val endTime: Long,
    val departureDelay: Long,
    val arrivalDelay: Long,
    val realTime: Boolean,
    val distance: Double,
    val generalizedCost: Long,
    val pathway: Boolean,
    val mode: VehicleType,
    val transitLeg: Boolean,
    val route: String,
    val agencyTimeZoneOffset: Long,
    val interlineWithPreviousLeg: Boolean,
    val from: OtpPlaceDto,
    val to: OtpPlaceDto,
    val legGeometry: OtpLegGeometryDto,
    val steps: List<OtpStepDto>,
    val rentedBike: Boolean,
    val walkingBike: Boolean,
    val duration: Long,
) {
    fun toGaiaxRoutingGeometryDto(): RoutingGeometryDto {
        val routingGeometry = RoutingGeometryDto()
        routingGeometry.coordinates = steps.map { point ->
            listOf(point.lon, point.lat)
        }.toList()
        return routingGeometry
    }

}
