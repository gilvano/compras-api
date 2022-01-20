package com.gilvano.comprasapi.component

import com.gilvano.comprasapi.service.impl.CashbackCalculatorImpl
import org.springframework.stereotype.Component

@Component
class CashbackCalculatorFactory {
    private val tenPercentCashback = 0.1
    private val fifteenPercentCashback = 0.15
    private val twentyPercentCashback = 0.2

    fun create(value: Double) = when {
            value > 1500 -> CashbackCalculatorImpl(twentyPercentCashback)
            value > 1000 -> CashbackCalculatorImpl(fifteenPercentCashback)
            else -> CashbackCalculatorImpl(tenPercentCashback)
        }
}