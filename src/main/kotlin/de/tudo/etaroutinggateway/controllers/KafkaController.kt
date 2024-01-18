package de.tudo.etaroutinggateway.controllers

import de.tudo.etaroutinggateway.entities.dtos.RoutingRequestDto
import de.tudo.etaroutinggateway.services.RoutingMappingService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class KafkaController(
    @Autowired
    private val routingMappingService: RoutingMappingService
) {
    @KafkaListener(topics = ["\${gaiax.kafka.request-topic}"], groupId = "routing-gateway-listener", containerFactory = "routingRequestListener")
    fun listenRoutingRequest(consumerRecord: ConsumerRecord<String, RoutingRequestDto>) {
        routingMappingService.handleRouteRequestAndSendResponse(consumerRecord.value())
    }
}