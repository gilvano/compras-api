package com.gilvano.comprasapi.exception

import org.springframework.http.HttpStatus

class ExternalCommunicationFailureException(val errorMessage: String, val statusCode: HttpStatus) : Exception()