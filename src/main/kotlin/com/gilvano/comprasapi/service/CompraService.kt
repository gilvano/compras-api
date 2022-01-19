package com.gilvano.comprasapi.service

import com.gilvano.comprasapi.model.CompraModel

interface CompraService {
    fun create(compra: CompraModel)
}