package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.enums.PurchaseStatus
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.repository.PurchaseRepository
import com.gilvano.comprasapi.service.PurchaseService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : PurchaseService {

    override fun create(purchase: PurchaseModel) {
        purchaseRepository.save(purchase)

        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchase))
    }

    override fun updatePurchaseStatus(purchase: PurchaseModel) {
        purchaseRepository.save(purchase)
    }

    override fun findAll(pageable: Pageable): Page<PurchaseModel> {
        return purchaseRepository.findAll(pageable)
    }

}