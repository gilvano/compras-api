package com.gilvano.comprasapi.controller.request

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import org.springframework.format.annotation.DateTimeFormat
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.Digits
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class PurchaseRequest(

    @NotEmpty(message = "O campo 'id' não pode ser vazio.")
    @field:Positive(message = "O campo 'id' deve ser um número positivo.")
    val id: Long,

    @NotEmpty(message = "O campo 'valor' não pode ser vazio.")
    @field:Digits(integer = 10, fraction = 2, message = "O campo 'valor' deve conter apenas números.")
    var valor: BigDecimal,

    @NotEmpty(message = "O campo 'data' não pode ser vazio.")
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    var data: LocalDate,

    @NotEmpty(message = "CPF não pode ser vazio.")
    @field:CPF(message="CPF inválido")
    @field:Length(min = 11, max = 11, message = "CPF deve conter 11 caracteres.")
    @field:Digits(integer = 11, fraction = 0, message = "CPF deve conter apenas números.")
    var cpf: String,
)
