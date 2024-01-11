package de.tudo.etaroutinggateway.entities

import java.util.UUID

class RoutingRequest(
    val requestId: UUID,
    val routeLocations: List<RouteLocation>,
    val metadata: Metadata
) {
}