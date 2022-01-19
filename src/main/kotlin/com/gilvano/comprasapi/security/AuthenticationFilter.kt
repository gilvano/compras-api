package com.gilvano.comprasapi.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gilvano.comprasapi.controller.request.LoginRequest
import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.exception.AuthenticationException
import com.gilvano.comprasapi.repository.RevendedorRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val revendedorRepository: RevendedorRepository
) : UsernamePasswordAuthenticationFilter(
    authenticationManager
) {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val loginRequest = jacksonObjectMapper().readValue(request?.inputStream, LoginRequest::class.java)
            val id = revendedorRepository.findByEmail(loginRequest.email)?.id
            val authToken = UsernamePasswordAuthenticationToken(id, loginRequest.senha)
            return authenticationManager.authenticate(authToken)
        } catch (e: Exception) {
            throw AuthenticationException(Errors.CP999.message, Errors.CP999.code)
        }
    }
}
