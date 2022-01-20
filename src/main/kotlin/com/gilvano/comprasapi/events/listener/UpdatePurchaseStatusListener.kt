package com.gilvano.comprasapi.events.listener

import com.gilvano.comprasapi.enums.PurchaseStatus
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdatePurchaseStatusListener(
    private val purchaseService: PurchaseService
) {
    private val CPF_WITH_AUTO_APPROVAL = "15350946056"

    @Async
    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        if (purchaseEvent.purchaseModel.cpf.equals(CPF_WITH_AUTO_APPROVAL)) {
            purchaseEvent.purchaseModel.status = PurchaseStatus.APROVADO
            purchaseService.updatePurchaseStatus(purchaseEvent.purchaseModel)
        }
    }
}