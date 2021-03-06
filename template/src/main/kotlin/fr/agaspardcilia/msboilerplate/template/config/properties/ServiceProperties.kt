package fr.agaspardcilia.msboilerplate.template.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
data class ServiceProperties(
    val security: Security = Security()
)
