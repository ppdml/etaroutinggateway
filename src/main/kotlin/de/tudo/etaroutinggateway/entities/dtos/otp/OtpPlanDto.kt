package de.tudo.etaroutinggateway.entities.dtos.otp

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class OtpPlanDto(
    @JsonFormat(without = [JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS])
    val date: Date,
    val from: OtpPlaceDto,
    val to: OtpPlaceDto,
    val itineraries: List<OtpItineraryDto>,
)