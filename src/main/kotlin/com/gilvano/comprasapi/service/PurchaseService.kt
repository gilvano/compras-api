package com.gilvano.comprasapi.service

import com.gilvano.comprasapi.model.PurchaseModel

interface PurchaseService {
    fun create(purchase: PurchaseModel)
    fun updatePurchaseStatus(purchase: PurchaseModel)
}