package com.gilvano.comprasapi.extension

import com.gilvano.comprasapi.controller.request.CompraRequest
import com.gilvano.comprasapi.controller.request.ResellerRequest
import com.gilvano.comprasapi.model.CompraModel
import com.gilvano.comprasapi.model.ResellerModel
import java.util.UUID

fun ResellerRequest.toResellerModel() =
    ResellerModel(
        id = UUID.randomUUID().toString(),
        name = name,
        cpf = cpf,
        email = email,
        password = password
    )

fun CompraRequest.toCompraModel() =
    CompraModel(
        id = id,
        cpf = cpf,
        valor = valor,
        data = data
    )