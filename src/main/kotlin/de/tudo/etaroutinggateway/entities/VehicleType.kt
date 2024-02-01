package de.tudo.etaroutinggateway.entities

import com.fasterxml.jackson.annotation.JsonValue

enum class VehicleType(@JsonValue val type: String) {
    CAR("CAR"),
    TRUCK("TRUCK"),
}
