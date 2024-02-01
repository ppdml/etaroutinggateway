package de.tudo.etaroutinggateway.entities

import com.fasterxml.jackson.annotation.JsonValue

enum class FeatureType(@JsonValue val value: String) {
    FEATURE_COLLECTION("FeatureCollection"),
    FEATURE("Feature"),
    POINT("Point"),
    LINE_STRING("LineString"),
    POLYGON("Polygon"),
}
