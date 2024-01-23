package de.tudo.etaroutinggateway.entities.dtos.otp

import com.fasterxml.jackson.annotation.JsonFormat
import de.tudo.etaroutinggateway.entities.VertexType
import java.util.*

data class OtpPlaceDto(
    val name: String,
    val lat: Double,
    val lon: Double,
    val vertexType: VertexType,
    @JsonFormat(without = [JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS])
    val departure: Date?,
    @JsonFormat(without = [JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS])
    val arrival: Date?,
)
