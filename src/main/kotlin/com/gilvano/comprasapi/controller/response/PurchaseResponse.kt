package com.gilvano.comprasapi.controller.response

import com.gilvano.comprasapi.enums.PurchaseStatus
import java.time.LocalDate

data class PurchaseResponse(
    val id: Long,
    val value: Double,
    val date: LocalDate,
    val cashbackPercentage: Double,
    val cashbackValue: Double,
    val status: PurchaseStatus
)
