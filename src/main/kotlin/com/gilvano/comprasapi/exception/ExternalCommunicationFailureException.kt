package com.gilvano.comprasapi.exception

import org.springframework.http.HttpStatus

class ExternalCommunicationFailureException(val statusCode: HttpStatus, val errorMessage: String?) : Exception()