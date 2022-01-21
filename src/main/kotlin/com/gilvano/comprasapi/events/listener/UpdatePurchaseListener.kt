package com.gilvano.comprasapi.events.listener

import com.gilvano.comprasapi.component.CashbackProcessor
import com.gilvano.comprasapi.enums.PurchaseStatus
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.service.PurchaseService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdatePurchaseListener(
    private val purchaseService: PurchaseService,
    private val cashbackProcessor: CashbackProcessor
) {
    private val CPF_WITH_AUTO_APPROVAL = "15350946056"
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Async
    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        logger.info("Received event: $purchaseEvent")
        updatePurchaseStatus(purchaseEvent.purchaseModel)
        cashbackProcessor.processAssociatedPurchases(purchaseEvent.purchaseModel)
    }

    private fun updatePurchaseStatus(purchaseModel: PurchaseModel) {
        logger.info("Updating purchase status: $purchaseModel")
        if (purchaseModel.cpf.equals(CPF_WITH_AUTO_APPROVAL)) {
            logger.info("Updating purchase status: $purchaseModel")
            purchaseModel.status = PurchaseStatus.APPROVED
            purchaseService.updatePurchaseStatus(purchaseModel)
            logger.info("Purchase status updated: $purchaseModel")
        }
    }
}