package com.gilvano.comprasapi.controller.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
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
)
