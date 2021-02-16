package fr.agaspardcilia.msboilerplate.authentication.user.dto

import fr.agaspardcilia.msboilerplate.authentication.utils.MAX_PASSWORD_SIZE
import fr.agaspardcilia.msboilerplate.authentication.utils.MIN_PASSWORD_SIZE
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ForgottenPasswordDto(
    @field:NotNull
    val token: String?,

    @field:NotNull
    @field:Size(min = MIN_PASSWORD_SIZE, max = MAX_PASSWORD_SIZE)
    val newPassword: String?
)
