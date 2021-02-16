package fr.agaspardcilia.msboilerplate.authentication.user.authentication

import fr.agaspardcilia.msboilerplate.authentication.user.User
import fr.agaspardcilia.msboilerplate.authentication.user.UserRepository
import fr.agaspardcilia.msboilerplate.common.exception.ApiForbiddenException
import fr.agaspardcilia.msboilerplate.common.exception.ApiNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("userDetailsService")
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun loadUserByUsername(mail: String): UserDetails {
        log.debug("Authenticating {}", mail)
        when (val maybeUser = userRepository.findByMail(mail.toLowerCase())) {
            null -> throw ApiNotFoundException("Unable to find user '$mail'")
            else -> return createSpringSecurityUser(maybeUser)
        }
    }

    private fun createSpringSecurityUser(user: User): org.springframework.security.core.userdetails.User {
        if (!user.isActive) {
            throw ApiForbiddenException("'${user.mail}' is not yet activated")
        }
        val grantedAuthorities = user.authorities.map { SimpleGrantedAuthority(it.name) }
        return org.springframework.security.core.userdetails.User(
            user.mail!!,
            user.password!!,
            grantedAuthorities
        )
    }

}
