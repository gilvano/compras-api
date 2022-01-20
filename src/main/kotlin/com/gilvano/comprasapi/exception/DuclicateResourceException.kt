package com.gilvano.comprasapi.exception

class DuclicateResourceException(override val message: String, val errorCode: String): Exception() {
}