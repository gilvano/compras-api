package com.gilvano.comprasapi.controller

import com.gilvano.comprasapi.controller.request.CompraRequest
import com.gilvano.comprasapi.controller.request.RevendedorRequest
import com.gilvano.comprasapi.extension.toCompraModel
import com.gilvano.comprasapi.service.CompraService
import com.gilvano.comprasapi.service.impl.CompraServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/compras")
class ComprasController(
    private val compraService: CompraService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody compra: CompraRequest) =
         compraService.create(compra.toCompraModel())
}