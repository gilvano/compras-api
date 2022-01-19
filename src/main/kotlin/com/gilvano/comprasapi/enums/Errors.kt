package com.gilvano.comprasapi.enums

enum class Errors(val code: String, val message: String) {
    CP000("CP000", "Requisição inválida"),
    CP001("CP001", "CPF já cadastrado"),
    CP002("CP002", "E-mail já cadastrado"),
}