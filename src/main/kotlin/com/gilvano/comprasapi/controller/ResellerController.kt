package com.gilvano.comprasapi.controller

import com.gilvano.comprasapi.controller.request.LoginRequest
import com.gilvano.comprasapi.controller.request.ResellerRequest
import com.gilvano.comprasapi.controller.response.LoginResponse
import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.exception.AuthenticationException
import com.gilvano.comprasapi.extension.toResellerModel
import com.gilvano.comprasapi.security.JwtUtil
import com.gilvano.comprasapi.security.UserCustomDetails
import com.gilvano.comprasapi.service.ResellerService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/api/v1/resellers")
class ResellerController(
    private val resellerService: ResellerService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody resellerRequest: ResellerRequest) =
        resellerService.create(resellerRequest.toResellerModel())

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse>  {
        try {
            val id = resellerService.findByEmail(loginRequest.email)?.id
            val authToken = UsernamePasswordAuthenticationToken(id, loginRequest.senha)
            val authentication: Authentication = authenticationManager.authenticate(authToken)
            val userDetails = authentication.principal as UserCustomDetails
            val token = jwtUtil.generateToken(userDetails.id)
            return ResponseEntity.ok(LoginResponse("User authenticated successfully", token))
        } catch (e: Exception) {
            throw AuthenticationException(Errors.CP999.message, Errors.CP999.code)
        }

    }
}
