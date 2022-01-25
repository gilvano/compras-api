package com.gilvano.comprasapi.service

import com.gilvano.comprasapi.model.ResellerModel

interface ResellerService {
    fun create(reseller: ResellerModel)
    fun getCpfFromLoggedReseller(): String
    fun findByEmail(email: String): ResellerModel
}