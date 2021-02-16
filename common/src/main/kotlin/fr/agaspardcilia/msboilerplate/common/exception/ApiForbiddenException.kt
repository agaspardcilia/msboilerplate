package fr.agaspardcilia.msboilerplate.common.exception

import org.springframework.http.HttpStatus

open class ApiForbiddenException(message: String)
    : ApiException(HttpStatus.FORBIDDEN, message)
