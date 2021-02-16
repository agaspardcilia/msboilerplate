package fr.agaspardcilia.msboilerplate.authentication.config

import fr.agaspardcilia.msboilerplate.common.security.PermissionInterceptor
import org.springframework.stereotype.Component

@Component
class AuthenticationPermissionInterceptor : PermissionInterceptor()
