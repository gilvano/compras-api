package com.gilvano.comprasapi.exception

class BadRequestException(override val message: String, val errorCode: String) : Exception() {
}