package fr.agaspardcilia.msboilerplate.common.exception

import org.springframework.http.HttpStatus

class ApiInternalServerErrorException(message: String)
    : ApiException(HttpStatus.INTERNAL_SERVER_ERROR, message)
