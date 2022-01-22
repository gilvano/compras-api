package com.gilvano.comprasapi.gateway



import com.gilvano.comprasapi.exception.ExternalCommunicationFailureException
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "external-api", url = "\${third-party.external-api-url}",
            configuration = [ClientErrorDecoder::class])
interface ExternalApiClient {

    @GetMapping("?cpf={cpf}")
    @Throws(ExternalCommunicationFailureException::class)
    fun getAccumulatedCashbackByCpf(
        @RequestHeader("token") token: Map<String, String>,
        @PathVariable("cpf") cpf: String
    ): CashbackResponse
}