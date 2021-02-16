package fr.agaspardcilia.msboilerplate.template

import fr.agaspardcilia.msboilerplate.template.config.properties.ServiceProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(ServiceProperties::class)
class TemplateApplication

fun main(args: Array<String>) {
	runApplication<TemplateApplication>(*args)
}
