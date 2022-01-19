package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.repository.PurchaseRepository
import com.gilvano.comprasapi.service.PurchaseService
import org.springframework.stereotype.Service

@Service
class CompraServiceImpl(
    private val purchaseRepository: PurchaseRepository
) : PurchaseService {
    override fun create(compra: PurchaseModel) {
        purchaseRepository.save(compra)
    }
}