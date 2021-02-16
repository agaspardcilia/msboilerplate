package fr.agaspardcilia.msboilerplate.gateway.config

import fr.agaspardcilia.msboilerplate.gateway.security.JWTConfigurer
import fr.agaspardcilia.msboilerplate.gateway.security.TokenProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration(
    private val tokenProvider: TokenProvider,
    private val corsFilter: CorsFilter,
) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    public override fun configure(http: HttpSecurity) {
        http
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
            .headers()
            .frameOptions()
            .disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .apply<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>(securityConfigurerAdapter())
    }

    private fun securityConfigurerAdapter() = JWTConfigurer(tokenProvider)

}

