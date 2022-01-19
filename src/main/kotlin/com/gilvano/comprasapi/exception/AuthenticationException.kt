package com.gilvano.comprasapi.exception

class AuthenticationException(override val message: String, val errorCode: String): Exception() {
}