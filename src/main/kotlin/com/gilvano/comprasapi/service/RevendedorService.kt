package com.gilvano.comprasapi.service

import com.gilvano.comprasapi.model.RevendedorModel

interface RevendedorService {
    fun create(revendedor: RevendedorModel)
}