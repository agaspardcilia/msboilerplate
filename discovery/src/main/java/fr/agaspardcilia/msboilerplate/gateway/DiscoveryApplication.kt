package fr.agaspardcilia.msboilerplate.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
open class DiscoveryApplication: SpringBootServletInitializer()

fun main(args: Array<String>) {
    runApplication<DiscoveryApplication>(*args)
}
