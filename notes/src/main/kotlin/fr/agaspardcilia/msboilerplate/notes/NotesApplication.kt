package fr.agaspardcilia.msboilerplate.notes

import fr.agaspardcilia.msboilerplate.notes.config.properties.ServiceProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(ServiceProperties::class)
class NotesApplication

fun main(args: Array<String>) {
	runApplication<NotesApplication>(*args)
}
