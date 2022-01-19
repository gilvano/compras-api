package com.gilvano.comprasapi.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gilvano.comprasapi.controller.response.ErrorResponse
import com.gilvano.comprasapi.enums.Errors
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val errorResponse = ErrorResponse(
            HttpServletResponse.SC_UNAUTHORIZED, Errors.CP996.message, Errors.CP996.code, null
        )

        response.outputStream.print(jacksonObjectMapper().writeValueAsString(errorResponse))

    }
}