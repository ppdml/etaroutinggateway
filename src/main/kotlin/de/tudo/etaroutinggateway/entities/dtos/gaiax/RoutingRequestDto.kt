package de.tudo.etaroutinggateway.entities.dtos.gaiax


data class RoutingRequestDto(
    val requestId: String = "",
    val routeLocations: List<RouteLocationDto> = emptyList(),
    val metadata: MetadataDto = MetadataDto(),
) {

}
