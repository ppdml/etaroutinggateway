package de.tudo.etaroutinggateway.entities.dtos.otp

import java.util.Date

data class OtpPlanDto(
    //val date: Date,
    val from: OtpPlaceDto,
    val to: OtpPlaceDto,
)