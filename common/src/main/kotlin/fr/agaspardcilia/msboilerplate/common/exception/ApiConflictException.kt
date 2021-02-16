package fr.agaspardcilia.msboilerplate.common.exception

import org.springframework.http.HttpStatus

open class ApiConflictException(message: String)
    : ApiException(HttpStatus.CONFLICT, message)
