package com.gilvano.comprasapi.helper

import com.gilvano.comprasapi.model.RevendedorModel
import java.util.UUID

fun buildRevendedor(
    id: UUID? = UUID.randomUUID(),
    nome: String = "Revendedor Teste",
    cpf: String = "12345678901",
    email: String = "${UUID.randomUUID()}@email.com",
    senha: String = "senha",
) = RevendedorModel(
    id = id,
    nome = nome,
    cpf = cpf,
    email = email,
    senha = senha,
)