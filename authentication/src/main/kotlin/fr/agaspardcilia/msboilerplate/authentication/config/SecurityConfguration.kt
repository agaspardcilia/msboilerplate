package fr.agaspardcilia.msboilerplate.authentication.config

import fr.agaspardcilia.msboilerplate.authentication.security.JWTConfigurer
import fr.agaspardcilia.msboilerplate.authentication.user.authentication.TokenProvider
import fr.agaspardcilia.msboilerplate.common.security.Authority
import org.springframework.beans.factory.BeanInitializationException
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter
import javax.annotation.PostConstruct

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration(
    private val corsFilter: CorsFilter,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val userDetailsService: UserDetailsService,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

    @PostConstruct
    fun init() {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
        } catch (e: java.lang.Exception) {
            throw BeanInitializationException("Security configuration failed", e)
        }
    }

    override fun configure(web: WebSecurity?) {
        web!!.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
    }

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
            .antMatchers("/authenticate").permitAll()
            .antMatchers("/users/register").permitAll()
            .antMatchers("/users/password").permitAll()
            .antMatchers("/users/activate/*").permitAll()
            .antMatchers("/users/forgotten/*").permitAll()
            .antMatchers("/users/reset").permitAll()
            .antMatchers("/users/current-user").authenticated()
            .antMatchers("/users/**").hasAnyAuthority(Authority.ADMIN.name)
            .anyRequest().authenticated()
            .and()
            .apply<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>(securityConfigurerAdapter())
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    private fun securityConfigurerAdapter() = JWTConfigurer(tokenProvider)

}

