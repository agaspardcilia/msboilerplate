package fr.agaspardcilia.msboilerplate.common.security.annotations

import fr.agaspardcilia.msboilerplate.common.security.Permission

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class PermissionRequired(val value: Permission)
