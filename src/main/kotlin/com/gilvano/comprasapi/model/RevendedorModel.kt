package com.gilvano.comprasapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "revendedor")
data class RevendedorModel(

    @Id
    var id: UUID? = null,
    val nome: String,
    val cpf: String,
    val email: String,
    val senha: String
)