package com.gilvano.comprasapi.model

import com.gilvano.comprasapi.enums.CompraStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

@Document(collection = "compra")
data class CompraModel(

    @Id
    var id: Long,

    @NotBlank
    var valor: BigDecimal,

    @NotBlank
    var data: LocalDate,

    @NotBlank
    val cpf: String,

    var status: CompraStatus = CompraStatus.EM_VALIDACAO


)