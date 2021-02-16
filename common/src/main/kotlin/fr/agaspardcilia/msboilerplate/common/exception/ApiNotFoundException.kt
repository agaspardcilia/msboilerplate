package fr.agaspardcilia.msboilerplate.common.exception

import org.springframework.http.HttpStatus

class ApiNotFoundException(message: String)
    : ApiException(HttpStatus.NOT_FOUND, message)
