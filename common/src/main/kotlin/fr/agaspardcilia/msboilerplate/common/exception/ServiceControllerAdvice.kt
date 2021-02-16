package fr.agaspardcilia.msboilerplate.common.exception

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

open class ServiceControllerAdvice {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler
    fun handleApiException(apiException: ApiException): ResponseEntity<ApiExceptionResponse> {
        log.error("${apiException.status}: ${apiException.message}", apiException)

        return ResponseEntity(
            ApiExceptionResponse(
                httpStatus = apiException.status,
                message = apiException.message ?: "No message"
            ),
            apiException.status
        )
    }

    data class ApiExceptionResponse(
        val httpStatus: HttpStatus,
        val message: String,
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        val timestamp: LocalDateTime = LocalDateTime.now()
    )

}
