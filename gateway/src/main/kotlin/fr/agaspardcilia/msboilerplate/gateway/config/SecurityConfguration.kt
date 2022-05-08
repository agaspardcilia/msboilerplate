package fr.agaspardcilia.msboilerplate.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableReactiveMethodSecurity
class SecurityConfiguration {

    @Bean
    fun securitygWebFilterChain(
        http: ServerHttpSecurity
    ): SecurityWebFilterChain? {
        return http
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .authorizeExchange()
            .anyExchange()
            .permitAll()
            .and()
            .build()
    }
}

