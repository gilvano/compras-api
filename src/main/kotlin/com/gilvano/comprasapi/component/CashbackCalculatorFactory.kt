package com.gilvano.comprasapi.component

import com.gilvano.comprasapi.service.impl.CashbackCalculatorImpl
import org.springframework.stereotype.Component

@Component
class CashbackCalculatorFactory {
    private val cashbackFactorLow = 0.1
    private val cashbackFactorMedium = 0.15
    private val cashbackFactorHigh = 0.2

    fun create(value: Double) = when {
            value > 1500 -> CashbackCalculatorImpl(cashbackFactorHigh)
            value > 1000 -> CashbackCalculatorImpl(cashbackFactorMedium)
            else -> CashbackCalculatorImpl(cashbackFactorLow)
        }
}