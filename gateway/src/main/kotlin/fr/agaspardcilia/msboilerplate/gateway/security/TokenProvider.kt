package fr.agaspardcilia.msboilerplate.gateway.security

import fr.agaspardcilia.msboilerplate.gateway.config.properties.ServiceProperties
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import javax.annotation.PostConstruct

private const val AUTHORITIES_KEY = "auth"

@Component
class TokenProvider(
    private val serviceProperties: ServiceProperties
) {

    private val log = LoggerFactory.getLogger(javaClass)

    private var key: Key? = null

    private var tokenValidityInMilliseconds: Long = 0

    private var tokenValidityInMillisecondsForRememberMe: Long = 0

    @PostConstruct
    fun init() {
        val keyBytes = Decoders.BASE64.decode(serviceProperties.security.jwt.secret);

        this.key = Keys.hmacShaKeyFor(keyBytes)
        this.tokenValidityInMilliseconds = 1000 * serviceProperties.security.jwt.tokenValidityInSeconds
        this.tokenValidityInMillisecondsForRememberMe = 1000 * serviceProperties.security.jwt.tokenValidityInSecondsForRememberMe
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        val authorities = claims[AUTHORITIES_KEY].toString().splitToSequence(",")
            .mapTo(mutableListOf()) { SimpleGrantedAuthority(it) }

        val principal = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken)
            return true
        } catch (e: JwtException) {
            log.info("Invalid JWT token.")
            log.trace("Invalid JWT token trace.", e)
        } catch (e: IllegalArgumentException) {
            log.info("Invalid JWT token.")
            log.trace("Invalid JWT token trace.", e)
        }

        return false
    }
}
