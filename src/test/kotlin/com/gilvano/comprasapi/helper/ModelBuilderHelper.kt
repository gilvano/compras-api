package com.gilvano.comprasapi.helper

import com.gilvano.comprasapi.enums.PurchaseStatus
import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.model.ResellerModel
import java.time.LocalDate
import java.util.Random
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

fun buildPurchase(
    id: Long = Random().nextLong(),
    cpf: String = "12345678901",
    value: Double = 10.0,
    status: PurchaseStatus = PurchaseStatus.EM_VALIDACAO,
    date: LocalDate = LocalDate.now(),
    ) = PurchaseModel(
        id = id,
        cpf = cpf,
        value = value,
        status = status,
        date = date
    )