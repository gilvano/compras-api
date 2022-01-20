package com.gilvano.comprasapi.model

import com.gilvano.comprasapi.enums.PurchaseStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import javax.validation.constraints.NotBlank

@Document(collection = "purchases")
data class PurchaseModel(

    @Id
    var id: Long,

    @NotBlank
    var value: Double,

    @NotBlank
    var date: LocalDate,

    @NotBlank
    val cpf: String,

    var status: PurchaseStatus = PurchaseStatus.IN_VALIDATION,

    var cashback: Double = 0.0,

    var cashbackPercentage: Double = 0.0


)