package com.gilvano.comprasapi.controller

import com.gilvano.comprasapi.controller.request.PurchaseRequest
import com.gilvano.comprasapi.controller.response.PageResponse
import com.gilvano.comprasapi.controller.response.PurchaseResponse
import com.gilvano.comprasapi.extension.toPageResponse
import com.gilvano.comprasapi.extension.toPurchaseModel
import com.gilvano.comprasapi.extension.toPurchaseResponse
import com.gilvano.comprasapi.service.PurchaseService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/purchases")
@SecurityRequirement(name = "bearerAuth")
class PurchaseController(
    private val purchaseService: PurchaseService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody purchase: PurchaseRequest) =
         purchaseService.create(purchase.toPurchaseModel())

    @GetMapping("/all")
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): PageResponse<PurchaseResponse> =
        purchaseService.findAll(pageable).map { it.toPurchaseResponse() }.toPageResponse()

    @GetMapping("/accumulated")
    fun accumulated(): Double = purchaseService.getAccumulatedCashback()

}