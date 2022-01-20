package com.gilvano.comprasapi.events.listener

import com.gilvano.comprasapi.component.CashbackProcessor
import com.gilvano.comprasapi.enums.PurchaseStatus
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdatePurchaseListener(
    private val purchaseService: PurchaseService,
    private val cashbackProcessor: CashbackProcessor
) {
    private val CPF_WITH_AUTO_APPROVAL = "15350946056"

    @Async
    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        updatePurchaseStatus(purchaseEvent.purchaseModel)
        cashbackProcessor.processAssociatedPurchases(purchaseEvent.purchaseModel)
    }

    private fun updatePurchaseStatus(purchaseModel: PurchaseModel) {
        if (purchaseModel.cpf.equals(CPF_WITH_AUTO_APPROVAL)) {
            purchaseModel.status = PurchaseStatus.APROVADO
            purchaseService.updatePurchaseStatus(purchaseModel)
        }
    }
}