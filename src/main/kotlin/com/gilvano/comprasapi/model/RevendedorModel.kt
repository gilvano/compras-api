package com.gilvano.comprasapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID
import javax.validation.constraints.NotBlank

@Document(collection = "revendedor")
data class RevendedorModel(

    @Id
    var id: UUID? = null,

    @NotBlank
    val nome: String,

    @NotBlank
    val cpf: String,

    @NotBlank
    val email: String,

    @NotBlank
    val senha: String
)