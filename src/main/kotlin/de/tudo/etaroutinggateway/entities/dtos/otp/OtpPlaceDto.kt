package de.tudo.etaroutinggateway.entities.dtos.otp

import de.tudo.etaroutinggateway.entities.VertexType

data class OtpPlaceDto(
    val name: String,
    val lat: Double,
    val lon: Double,
    //val vertexType: VertexType,
)
