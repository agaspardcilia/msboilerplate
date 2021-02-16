package fr.agaspardcilia.msboilerplate.gateway

import fr.agaspardcilia.msboilerplate.gateway.config.properties.ServiceProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.netflix.zuul.EnableZuulServer

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableConfigurationProperties(ServiceProperties::class)
class GatewayApplication: SpringBootServletInitializer()

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}
