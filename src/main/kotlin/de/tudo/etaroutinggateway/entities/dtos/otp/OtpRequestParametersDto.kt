package de.tudo.etaroutinggateway.entities.dtos.otp

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class OtpRequestParametersDto(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    val date: Date,
    val mode: String,
    val arriveBy: Boolean,
    val wheelchair: Boolean,
    val showIntermediateStops: Boolean,
    val fromPlace: String,
    val toPlace: String,
    //val time: Datetime,
    val locale: String,
)