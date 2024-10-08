package de.tudo.etaroutinggateway.services

import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    @Autowired
    private val kafkaRequestTemplate: KafkaTemplate<String, RoutingRequestDto>,
    @Autowired
    private val kafkaResponseTemplate: KafkaTemplate<String, RoutingResponseDto>,
    @Value("\${gaiax.kafka.request-topic}")
    private val requestTopic: String,
    @Value("\${gaiax.kafka.response-topic}")
    private val responseTopic: String
) {
    fun sendRoutingRequest(routingRequest: RoutingRequestDto) {
        kafkaRequestTemplate.send(requestTopic, routingRequest)
    }

    fun sendRoutingResponse(routingResponse: RoutingResponseDto, routingRequestId: String) {
        routingResponse.requestId = routingRequestId
        kafkaResponseTemplate.send(responseTopic, routingResponse)
    }
}