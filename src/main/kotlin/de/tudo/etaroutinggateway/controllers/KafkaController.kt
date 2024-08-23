package de.tudo.etaroutinggateway.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.services.OtpRouteMappingService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.support.converter.ConversionException
import org.springframework.kafka.support.serializer.DeserializationException
import org.springframework.messaging.converter.MessageConversionException
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component


@Component
class KafkaController(
    @Autowired
    private val otpRouteMappingService: OtpRouteMappingService,
) {
    @RetryableTopic(
        kafkaTemplate = "routingRequestKafkaTemplate",
        exclude = [
            DeserializationException::class,
            MessageConversionException::class,
            ConversionException::class,
            MethodArgumentResolutionException::class,
            NoSuchMethodException::class,
            ClassCastException::class
        ],
        attempts = "3",
        backoff = Backoff(delay = 2000)
    )
    @KafkaListener(
        topics = ["\${gaiax.kafka.request-topic}"],
        groupId = "eta-routing-gateway",
        containerFactory = "routingRequestListener"
    )
    fun listenRoutingRequest(consumerRecord: ConsumerRecord<String, String>) {
        var mapper = ObjectMapper()
        try {
            val data = mapper.readValue(consumerRecord.value(), RoutingRequestDto::class.java)
            otpRouteMappingService.handleRouteRequestAndSendResponse(data)
        } catch (e: Exception) {
            println("Error while processing message: ${e.message}")
        }
    }

    @DltHandler
    fun processMessage(message: RoutingRequestDto?) {
        println("DltHandler processMessage = {}".format(message))
    }
}