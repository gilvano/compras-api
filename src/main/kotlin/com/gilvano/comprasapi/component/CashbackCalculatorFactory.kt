package com.gilvano.comprasapi.component

import com.gilvano.comprasapi.service.impl.CashbackCalculatorImpl
import org.springframework.stereotype.Component

@Component
class CashbackCalculatorFactory {
    fun create(value: Double) = when {
            value > 1500 -> CashbackCalculatorImpl(0.2)
            value > 1000 -> CashbackCalculatorImpl(0.15)
            else -> CashbackCalculatorImpl(0.1)
        }
}