package com.gilvano.comprasapi.extension

import com.gilvano.comprasapi.controller.request.CompraRequest
import com.gilvano.comprasapi.controller.request.RevendedorRequest
import com.gilvano.comprasapi.model.CompraModel
import com.gilvano.comprasapi.model.RevendedorModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

fun RevendedorRequest.toRevendedorModel() =
    RevendedorModel(
        id = UUID.randomUUID().toString(),
        nome = nome,
        cpf = cpf,
        email = email,
        senha = senha
    )

fun CompraRequest.toCompraModel() =
    CompraModel(
        id = id,
        cpf = cpf,
        valor = valor,
        data = data
        //data = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    )