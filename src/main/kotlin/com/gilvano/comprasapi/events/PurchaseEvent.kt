package com.gilvano.comprasapi.events

import com.gilvano.comprasapi.model.PurchaseModel
import org.springframework.context.ApplicationEvent

class PurchaseEvent(
    source: Any,
   val purchaseModel: PurchaseModel
): ApplicationEvent(source)
