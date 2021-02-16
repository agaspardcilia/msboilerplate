package fr.agaspardcilia.msboilerplate.authentication.user.dto

import javax.validation.constraints.NotNull

data class LoginDto(
    @field:NotNull
    val username: String?,

    @field:NotNull
    val password: String?,

    val rememberMe: Boolean = false
)
