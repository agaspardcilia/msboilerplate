package fr.agaspardcilia.msboilerplate.gateway.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
class ServiceProperties {
    var security: Security = Security()
}
