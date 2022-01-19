package com.gilvano.comprasapi.helper

import com.gilvano.comprasapi.model.ResellerModel
import java.util.UUID

fun buildReseller(
    id: String? = UUID.randomUUID().toString(),
    nome: String = "Reseller Test",
    cpf: String = "12345678901",
    email: String = "${UUID.randomUUID()}@email.com",
    password: String = "password",
) = ResellerModel(
    id = id,
    name = nome,
    cpf = cpf,
    email = email,
    password = password,
)