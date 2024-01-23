package de.tudo.etaroutinggateway.entities.dtos.otp

import de.tudo.etaroutinggateway.entities.AbsoluteDirection
import de.tudo.etaroutinggateway.entities.RelativeDirection

data class OtpStepDto(
    val distance: Double,
    val relativeDirection: RelativeDirection,
    val streetName: String,
    val absoluteDirection: AbsoluteDirection,
    val stayOn: Boolean,
    val area: Boolean,
    val bogusName: Boolean,
    val lon: Double,
    val lat: Double,
    val elevation: String,
    val walkingBike: Boolean,
)