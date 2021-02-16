package fr.agaspardcilia.msboilerplate.authentication.user.authentication

import com.fasterxml.jackson.annotation.JsonProperty
import fr.agaspardcilia.msboilerplate.authentication.user.dto.LoginDto
import fr.agaspardcilia.msboilerplate.common.security.AUTHORIZATION_HEADER
import fr.agaspardcilia.msboilerplate.common.security.Permission
import fr.agaspardcilia.msboilerplate.common.security.annotations.NoParticularPermission
import fr.agaspardcilia.msboilerplate.common.security.annotations.PermissionRequired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping
class UserJWTController(
    private val jwtAuthenticationService: JWTAuthenticationService
) {

    @PostMapping("/authenticate")
    @PermissionRequired(Permission.AUTHENTICATION_JWT_AUTH)
    fun authorize(@Valid @RequestBody login: LoginDto): ResponseEntity<JWTToken> {
        val jwt = jwtAuthenticationService.authenticateWithJWT(login)
        val httpHeaders = HttpHeaders()

        httpHeaders.add(AUTHORIZATION_HEADER, "Bearer $jwt")
        return ResponseEntity(JWTToken(jwt), httpHeaders, HttpStatus.OK)
    }

    class JWTToken(@get:JsonProperty("id_token") var idToken: String?)
}
