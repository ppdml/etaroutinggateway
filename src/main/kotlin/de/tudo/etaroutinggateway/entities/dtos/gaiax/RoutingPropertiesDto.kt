package de.tudo.etaroutinggateway.entities.dtos.gaiax

import java.util.Date

data class RoutingPropertiesDto(
    val startTime: Date?,
    val endTime: Date?,
    val duration: Long?,
)
