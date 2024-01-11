package de.tudo.etaroutinggateway.entities.dtos

import java.util.*

data class RoutingRequestDto(
    val requestId: UUID,
    val routeLocations: List<RouteLocationDto>,
    val metadata: MetadataDto
) {

}
