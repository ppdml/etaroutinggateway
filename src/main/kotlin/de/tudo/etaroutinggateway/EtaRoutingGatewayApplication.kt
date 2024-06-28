package de.tudo.etaroutinggateway

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "Eta Routing Gateway", version = "1.0",
    description = "API for the Eta Routing Gateway"))
class EtaRoutingGatewayApplication

fun main(args: Array<String>) {
    runApplication<EtaRoutingGatewayApplication>(*args)
}
