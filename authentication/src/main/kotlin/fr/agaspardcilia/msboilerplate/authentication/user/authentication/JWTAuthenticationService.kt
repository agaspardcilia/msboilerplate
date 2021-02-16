package fr.agaspardcilia.msboilerplate.authentication.user.authentication

import fr.agaspardcilia.msboilerplate.authentication.user.UserService
import fr.agaspardcilia.msboilerplate.authentication.user.dto.LoginDto
import fr.agaspardcilia.msboilerplate.authentication.user.dto.UserDto
import fr.agaspardcilia.msboilerplate.common.exception.ApiForbiddenException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class JWTAuthenticationService(
    private val tokenProvider: TokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService
) {

    fun authenticateWithJWT(loginDto: LoginDto): String {
        val authentication: Authentication = this.authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password))
        when (val user: UserDto? = userService.getByMail(loginDto.username!!)) {
            null -> throw ApiForbiddenException("Wrong username/password combination!")
            else -> {
                if (!user.isActive) {
                    throw ApiForbiddenException("'${user.mail}' is not yet activated")
                }
                SecurityContextHolder.getContext().authentication = authentication
                return tokenProvider.createToken(authentication, loginDto.rememberMe)
            }
        }


    }
}
