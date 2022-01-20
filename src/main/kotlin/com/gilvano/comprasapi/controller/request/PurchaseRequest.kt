package com.gilvano.comprasapi.controller.request

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.Digits
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class PurchaseRequest(

    @NotEmpty(message = "Id is required")
    @field:Positive(message = "Id should be positive")
    val id: Long,

    @NotEmpty(message = "Value is required")
    @field:Positive(message = "Value should be positive")
    var value: Double,

    @NotEmpty(message = "Date is required")
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    var date: LocalDate,

    @NotEmpty(message = "CPF is required")
    @field:CPF(message="CPF invalid")
    @field:Length(min = 11, max = 11, message = "CPF must have 11 characters")
    @field:Digits(integer = 11, fraction = 0, message = "CPF must have 11 digits")
    var cpf: String,
)
