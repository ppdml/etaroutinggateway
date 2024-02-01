package de.tudo.etaroutinggateway.entities.dtos.gaiax

import de.tudo.etaroutinggateway.entities.FeatureType

data class RoutingFeatureDto(
    val type: FeatureType = FeatureType.FEATURE,
    val geometry: RoutingGeometryDto,
    val properties: RoutingPropertiesDto? = null,
)
