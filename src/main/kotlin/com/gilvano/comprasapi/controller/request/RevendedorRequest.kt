package com.gilvano.comprasapi.controller.request

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Digits
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class RevendedorRequest(

    @get:NotEmpty(message = "Nome não pode ser vazio.")
    @get:Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
    var nome: String,

    @get:NotEmpty(message = "CPF não pode ser vazio.")
    @get:CPF(message="CPF inválido")
    @get:Length(min = 11, max = 11, message = "CPF deve conter 11 caracteres.")
    @get:Digits(integer = 11, fraction = 0, message = "CPF deve conter apenas números.")
    var cpf: String,

    @get:NotEmpty(message = "Email é obrigatório")
    @get:Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
    @get:Email(message="Email inválido.")
    var email: String,

    @get:NotEmpty(message = "Senha é obrigatório")
    var senha: String
)