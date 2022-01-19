package com.gilvano.comprasapi.exception

import com.gilvano.comprasapi.controller.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(CpfDuplicadoException::class)
    fun handleCpfDuplicadoException(ex: CpfDuplicadoException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.message,
            ex.errorCode)

        return ResponseEntity(erro, HttpStatus.BAD_REQUEST)
    }
}