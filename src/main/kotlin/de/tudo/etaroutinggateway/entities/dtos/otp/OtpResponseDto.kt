package de.tudo.etaroutinggateway.entities.dtos.otp

data class OtpResponseDto(
    val requestParameters: OtpRequestParametersDto,
    val plan: OtpPlanDto?,
){
}