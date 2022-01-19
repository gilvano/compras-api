package com.gilvano.comprasapi.controller

import com.gilvano.comprasapi.controller.request.ResellerRequest
import com.gilvano.comprasapi.extension.toResellerModel
import com.gilvano.comprasapi.service.ResellerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/resellers")
class ResellerController(
         val resellerService: ResellerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody resellerRequest: ResellerRequest) =
        resellerService.create(resellerRequest.toResellerModel())

}
