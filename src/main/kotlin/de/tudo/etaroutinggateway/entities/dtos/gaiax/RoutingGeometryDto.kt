package de.tudo.etaroutinggateway.entities.dtos.gaiax

import de.tudo.etaroutinggateway.entities.FeatureType

data class RoutingGeometryDto(
    val type: FeatureType = FeatureType.LINE_STRING,
    var coordinates: List<List<Double>> = listOf(),
)