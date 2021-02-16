package fr.agaspardcilia.msboilerplate.template.config

import fr.agaspardcilia.msboilerplate.template.config.properties.ServiceProperties
import fr.agaspardcilia.msboilerplate.template.security.JWTConfigurer
import fr.agaspardcilia.msboilerplate.template.security.TokenProvider
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration(
    private val serviceProperties: ServiceProperties,
    private val tokenProvider: TokenProvider,
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf()
            .and()
            .cors().configurationSource{ serviceProperties.security.cors }
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .apply<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>(securityConfigurerAdapter())
    }

    private fun securityConfigurerAdapter() = JWTConfigurer(tokenProvider)


}
