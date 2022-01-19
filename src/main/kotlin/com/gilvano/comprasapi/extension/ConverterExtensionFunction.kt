package com.gilvano.comprasapi.extension

import com.gilvano.comprasapi.controller.request.RevendedorRequest
import com.gilvano.comprasapi.model.RevendedorModel
import java.util.UUID

fun RevendedorRequest.toRevendedorModel() =
    RevendedorModel(
        id = UUID.randomUUID().toString(),
        nome = nome,
        cpf = cpf,
        email = email,
        senha = senha
    )