package fr.agaspardcilia.msboilerplate.notes.config

import fr.agaspardcilia.msboilerplate.common.security.PermissionInterceptor
import org.springframework.stereotype.Component

@Component
class NotesPermissionInterceptor : PermissionInterceptor()
