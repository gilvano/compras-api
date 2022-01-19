package com.gilvano.comprasapi.controller.request

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Digits
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class ResellerRequest(

    @get:NotEmpty(message = "Name is required")
    @get:Length(min = 3, max = 200, message = "Name must have between 3 and 200 characters")
    var name: String,

    @get:NotEmpty(message = "CPF is required")
    @get:CPF(message="CPF invalid")
    @get:Length(min = 11, max = 11, message = "CPF must have 11 characters")
    @get:Digits(integer = 11, fraction = 0, message = "CPF must have only numbers")
    var cpf: String,

    @get:NotEmpty(message = "Email is required")
    @get:Length(min = 5, max = 200, message = "Email must have between 5 and 200 characters")
    @get:Email(message="Email invalid")
    var email: String,

    @get:NotEmpty(message = "Password is required")
    var password: String
)