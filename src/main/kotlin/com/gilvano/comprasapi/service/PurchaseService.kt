package com.gilvano.comprasapi.service

import com.gilvano.comprasapi.model.PurchaseModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PurchaseService {
    fun create(purchase: PurchaseModel)
    fun updatePurchaseStatus(purchase: PurchaseModel)
    fun findAll(pageable: Pageable): Page<PurchaseModel>
}