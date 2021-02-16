package fr.agaspardcilia.msboilerplate.common.security

import fr.agaspardcilia.msboilerplate.common.exception.ApiForbiddenException
import fr.agaspardcilia.msboilerplate.common.exception.ApiInternalServerErrorException
import fr.agaspardcilia.msboilerplate.common.security.annotations.NoParticularPermission
import fr.agaspardcilia.msboilerplate.common.security.annotations.PermissionRequired
import fr.agaspardcilia.msboilerplate.common.security.annotations.PermitAll
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class PermissionInterceptor : HandlerInterceptorAdapter() {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method.equals("OPTIONS", true)) {
            return super.preHandle(request, response, handler)
        }

        val methodHandler = handler as HandlerMethod
        val hasPermitAllAnnotation = methodHandler.hasMethodAnnotation(PermitAll::class.java)
        val hasPermissionRequired = methodHandler.hasMethodAnnotation(PermissionRequired::class.java)
        val hasNoParticularPermissionRequired = methodHandler.hasMethodAnnotation(NoParticularPermission::class.java)

        if (!(hasPermitAllAnnotation || hasPermissionRequired || hasNoParticularPermissionRequired)) {
            throw ApiInternalServerErrorException("Invalid endpoint configuration : '${request.requestURI}' has no configured Permission!")
        }

        if (hasPermissionRequired && hasNoParticularPermissionRequired) {
            throw ApiInternalServerErrorException("Invalid endpoint configuration : '${request.requestURI}' requires permission and no permission!")
        }

        if (!hasPermitAllAnnotation) {
            if (hasPermissionRequired) {
                val permission: Permission = methodHandler.getMethodAnnotation(PermissionRequired::class.java)!!.value
                val hasPermission = getCurrentUserAuthorities()
                    .asSequence()
                    .map { it.permissions }
                    .map { it.map { p -> p.name } }
                    .flatten()
                    .distinct()
                    .any { it == permission.name }

                if (!hasPermission) {
                    throw ApiForbiddenException("Current user is not authorized to perform this operation")
                }
            }
        }

        return super.preHandle(request, response, handler)
    }

    private fun getCurrentUserAuthorities(): List<Authority> {
        val result: ArrayList<Authority> = ArrayList()

        SecurityContextHolder.getContext()
            ?.let {
                result.addAll(
                    it.authentication.authorities
                        .map { auth -> Authority.valueOf(auth.authority) }
                )
            }

        return result
    }

}
