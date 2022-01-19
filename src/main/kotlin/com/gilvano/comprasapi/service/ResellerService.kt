package com.gilvano.comprasapi.service

import com.gilvano.comprasapi.model.ResellerModel

interface ResellerService {
    fun create(reseller: ResellerModel)
}