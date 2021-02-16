package fr.agaspardcilia.msboilerplate.common.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class ApiException(
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
    message: String
) : RuntimeException(message)
