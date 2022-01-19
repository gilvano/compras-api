package com.gilvano.comprasapi.controller

import com.gilvano.comprasapi.controller.request.PurchaseRequest
import com.gilvano.comprasapi.extension.toPurchaseModel
import com.gilvano.comprasapi.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/purchases")
class PurchaseController(
    private val purchaseService: PurchaseService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody purchase: PurchaseRequest) =
         purchaseService.create(purchase.toPurchaseModel())
}