package com.gilvano.comprasapi.extension

import com.gilvano.comprasapi.controller.request.PurchaseRequest
import com.gilvano.comprasapi.controller.request.ResellerRequest
import com.gilvano.comprasapi.controller.response.PageResponse
import com.gilvano.comprasapi.controller.response.PurchaseResponse
import com.gilvano.comprasapi.enums.PurchaseStatus
import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.model.ResellerModel
import org.springframework.data.domain.Page
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

fun PurchaseModel.toPurchaseResponse() =
    PurchaseResponse(
        id = id,
        value = value,
        date = date,
        cashbackPercentage = cashbackPercentage,
        cashbackValue = cashback,
        status = status
    )

fun <T> Page<T>.toPageResponse(): PageResponse<T> {
    return PageResponse(
        this.content,
        this.number,
        this.totalElements,
        this.totalPages)
}