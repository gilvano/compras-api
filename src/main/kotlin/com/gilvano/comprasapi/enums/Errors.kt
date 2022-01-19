package com.gilvano.comprasapi.enums

enum class Errors(val code: String, val message: String) {
    CP000("CP000", "Invalid request"),
    CP001("CP001", "CPF already registered"),
    CP002("CP002", "E-mail already registered"),
    CP996("CP996", "Unauthorized"),
    CP997("CP997", "Invalid token"),
    CP998("CP998", "User not found"),
    CP999("CP999", "Authentication failed"),
}