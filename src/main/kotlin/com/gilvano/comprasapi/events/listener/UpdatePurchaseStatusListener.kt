package com.gilvano.comprasapi.events.listener

import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdatePurchaseStatusListener(
    private val purchaseService: PurchaseService
) {
    @Async
    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        purchaseService.updatePurchaseStatus(purchaseEvent.purchaseModel)
    }
}