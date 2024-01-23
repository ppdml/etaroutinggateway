package de.tudo.etaroutinggateway.controllers

import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.services.OtpRouteMappingService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class KafkaController(
    @Autowired
    private val otpRouteMappingService: OtpRouteMappingService
) {
    @KafkaListener(topics = ["\${gaiax.kafka.request-topic}"], groupId = "routing-gateway-listener", containerFactory = "routingRequestListener")
    fun listenRoutingRequest(consumerRecord: ConsumerRecord<String, RoutingRequestDto>) {
        otpRouteMappingService.handleRouteRequestAndSendResponse(consumerRecord.value())
    }
}