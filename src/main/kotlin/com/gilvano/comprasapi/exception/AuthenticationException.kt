package com.gilvano.comprasapi.exception

import org.springframework.security.core.AuthenticationException as CoreAuthenticationException

class AuthenticationException(override val message: String, val errorCode: String): CoreAuthenticationException(message) {
}