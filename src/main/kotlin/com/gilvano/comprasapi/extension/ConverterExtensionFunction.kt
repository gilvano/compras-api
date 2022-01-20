package com.gilvano.comprasapi.extension

import com.gilvano.comprasapi.controller.request.PurchaseRequest
import com.gilvano.comprasapi.controller.request.ResellerRequest
import com.gilvano.comprasapi.model.PurchaseModel
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

fun PurchaseRequest.toPurchaseModel() =
    PurchaseModel(
        id = id,
        cpf = cpf,
        value = value,
        date = date
    )