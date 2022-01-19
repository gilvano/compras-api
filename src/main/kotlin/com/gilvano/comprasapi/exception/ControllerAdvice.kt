package com.gilvano.comprasapi.exception

import com.gilvano.comprasapi.controller.response.ErrorResponse
import com.gilvano.comprasapi.controller.response.FieldErrorResponse
import com.gilvano.comprasapi.enums.Errors
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(BadRequestException::class)
    fun handleCpfDuplicadoException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.message,
            ex.errorCode,
            null
        )

        return ResponseEntity(erro, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            Errors.CP000.message,
            Errors.CP000.code,
            ex.bindingResult.fieldErrors.map { FieldErrorResponse(it.defaultMessage ?: "invalid", it.field) } )

        return ResponseEntity(erro, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}