package com.gilvano.comprasapi.controller

import com.gilvano.comprasapi.controller.request.RevendedorRequest
import com.gilvano.comprasapi.extension.toRevendedorModel
import com.gilvano.comprasapi.service.RevendedorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/revendedor")
class RevendedorController(
         val revendedorService: RevendedorService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody revendedorRequest: RevendedorRequest) =
        revendedorService.create(revendedorRequest.toRevendedorModel())

}
