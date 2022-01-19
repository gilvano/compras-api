package com.gilvano.comprasapi.exception

class CpfDuplicadoException(override val message: String, val errorCode: String) : Exception() {
}