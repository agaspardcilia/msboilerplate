package fr.agaspardcilia.msboilerplate.notes.security

import fr.agaspardcilia.msboilerplate.common.security.AUTHORIZATION_HEADER
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
class JWTFilter(private val tokenProvider: TokenProvider) : OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse, filterChain: FilterChain) {
        val jwt = resolveToken(servletRequest)
        if (!jwt.isNullOrBlank() && this.tokenProvider.validateToken(jwt)) {
            val authentication = this.tokenProvider.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication
        } else {
            // Not sure about this, remove if it creates issues.
            SecurityContextHolder.clearContext()
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }
}
