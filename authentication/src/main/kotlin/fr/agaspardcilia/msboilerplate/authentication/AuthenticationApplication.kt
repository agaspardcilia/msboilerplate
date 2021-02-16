package fr.agaspardcilia.msboilerplate.authentication

import fr.agaspardcilia.msboilerplate.authentication.config.properties.ServiceProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(ServiceProperties::class)
class AuthenticationApplication

fun main(args: Array<String>) {
	runApplication<AuthenticationApplication>(*args)
}
