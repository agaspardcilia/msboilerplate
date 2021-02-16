package fr.agaspardcilia.msboilerplate.notes

import fr.agaspardcilia.msboilerplate.notes.config.properties.ServiceProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(ServiceProperties::class)
class NotesApplication

fun main(args: Array<String>) {
	runApplication<NotesApplication>(*args)
}
