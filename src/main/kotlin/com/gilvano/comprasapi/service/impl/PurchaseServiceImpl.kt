package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.enums.PurchaseStatus
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.repository.PurchaseRepository
import com.gilvano.comprasapi.service.PurchaseService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : PurchaseService {

    private val CPF_WITH_AUTO_APPROVAL = "15350946056"

    override fun create(purchase: PurchaseModel) {
        purchaseRepository.save(purchase)

        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchase))
    }

    override fun updatePurchaseStatus(purchase: PurchaseModel) {
        if (purchase.cpf.equals(CPF_WITH_AUTO_APPROVAL)) {
            purchase.status =  PurchaseStatus.APROVADO

            purchaseRepository.save(purchase)
        }
    }
}