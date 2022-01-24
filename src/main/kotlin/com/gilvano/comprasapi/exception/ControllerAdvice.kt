package com.gilvano.comprasapi.exception

import com.gilvano.comprasapi.controller.response.ErrorResponse
import com.gilvano.comprasapi.controller.response.FieldErrorResponse
import com.gilvano.comprasapi.enums.Errors
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.message,
            ex.errorCode,
            null
        )

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            Errors.CP000.message,
            Errors.CP000.code,
            ex.bindingResult.fieldErrors.map { FieldErrorResponse(it.defaultMessage ?: "invalid", it.field) } )

        return ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            Errors.CP996.message,
            Errors.CP996.code,
            null)

        return ResponseEntity(error, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(DuclicateResourceException::class)
    fun handleDuclicateResourceException(ex: DuclicateResourceException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.message,
            ex.errorCode,
            null
        )

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ExternalCommunicationFailureException::class)
    fun handleExternalCommunicationFailureException(ex: ExternalCommunicationFailureException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            ex.statusCode.value(),
            ex.errorMessage,
            ex.statusCode.toString(),
            null
        )

        return ResponseEntity(error, ex.statusCode)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            Errors.CP996.message,
            Errors.CP996.code,
            null)

        return ResponseEntity(error, HttpStatus.UNAUTHORIZED)

    }
}