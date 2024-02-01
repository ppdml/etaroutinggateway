package de.tudo.etaroutinggateway.config

import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingRequestDto
import de.tudo.etaroutinggateway.entities.dtos.gaiax.RoutingResponseDto
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
@EnableKafka
class KafkaConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val servers: String,
    @Value("\${gaiax.kafka.request-topic}")
    private val requestTopic: String,
) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = HashMap<String, Any>()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = servers
        return KafkaAdmin(configs)
    }

    @Bean
    fun routingRequestDtoKafkaConsumerFactory(): ConsumerFactory<String, RoutingRequestDto> {
        val payloadJsonDeserializer: JsonDeserializer<RoutingRequestDto> = JsonDeserializer<RoutingRequestDto>()
        payloadJsonDeserializer.addTrustedPackages(
            "de.tudo.etaroutinggateway.entities.dtos",
            "de.tudo.etaroutinggateway.entities.dtos.gaiax",
            "de.tudo.etaroutinggateway.entities.dtos.otp"
        )
        return DefaultKafkaConsumerFactory(jsonConsumerConfigs(), StringDeserializer(), payloadJsonDeserializer)
    }

    @Bean
    fun routingRequestListener(): ConcurrentKafkaListenerContainerFactory<String, RoutingRequestDto> {
        val factory
                : ConcurrentKafkaListenerContainerFactory<String, RoutingRequestDto> =
            ConcurrentKafkaListenerContainerFactory<String, RoutingRequestDto>()
        factory.consumerFactory = routingRequestDtoKafkaConsumerFactory()
        return factory
    }

    @Bean
    fun routingRequestDtoKafkaProducerFactory(): ProducerFactory<String, RoutingRequestDto> {
        return DefaultKafkaProducerFactory(jsonProducerConfigs())
    }

    @Bean
    fun routingRequestKafkaTemplate(): KafkaTemplate<String, RoutingRequestDto> {
        return KafkaTemplate(routingRequestDtoKafkaProducerFactory())
    }

    @Bean
    fun routingResponseDtoKafkaProducerFactory(): ProducerFactory<String, RoutingResponseDto> {
        return DefaultKafkaProducerFactory(jsonProducerConfigs())
    }

    @Bean
    fun routingResponseKafkaTemplate(): KafkaTemplate<String, RoutingResponseDto> {
        return KafkaTemplate(routingResponseDtoKafkaProducerFactory())
    }

    private fun jsonProducerConfigs(): MutableMap<String, Any> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = servers
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return configProps
    }

    private fun jsonConsumerConfigs(): MutableMap<String, Any> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = servers
        configProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        configProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return configProps
    }
}

