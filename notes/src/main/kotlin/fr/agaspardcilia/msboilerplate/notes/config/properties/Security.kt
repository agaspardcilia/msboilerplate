package fr.agaspardcilia.msboilerplate.notes.config.properties

import org.springframework.web.cors.CorsConfiguration
import kotlin.properties.Delegates

data class Security(
    var cors: CorsConfiguration = CorsConfiguration(),
    var jwt: Jwt = Jwt()
) {
    class Jwt {
        lateinit var secret: String
        var tokenValidityInSeconds by Delegates.notNull<Long>()
        var tokenValidityInSecondsForRememberMe by Delegates.notNull<Long>()
    }
}

