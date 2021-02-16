package fr.agaspardcilia.msboilerplate.authentication.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
class ServiceProperties {
    var security: Security = Security()
    var mail: Mail = Mail()
}
