package com.gilvano.comprasapi.gateway

import com.gilvano.comprasapi.exception.ExternalCommunicationFailureException
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus

class ClientErrorDecoder: ErrorDecoder {
    override fun decode(methodKey: String, response: Response) =
        ExternalCommunicationFailureException(response.reason(), HttpStatus.FAILED_DEPENDENCY)
}