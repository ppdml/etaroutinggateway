package de.tudo.etaroutinggateway.entities.dtos.gaiax

import de.tudo.etaroutinggateway.entities.FeatureType

data class RoutingResponseDto(
    var features: List<RoutingFeatureDto> = listOf(),
    val type: FeatureType = FeatureType.FEATURE_COLLECTION,
)