package de.tudo.etaroutinggateway.entities.dtos.gaiax

import java.util.Date

data class RoutingPropertiesDto(
    val startTime: Date? = null,
    val endTime: Date? = null,
    val duration: Long? = null,
    val startPlace: CoordinateDto? = null,
    val endPlace: CoordinateDto? = null,
)
