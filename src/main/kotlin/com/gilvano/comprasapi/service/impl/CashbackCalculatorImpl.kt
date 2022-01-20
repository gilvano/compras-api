package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.model.CashbackPercentage
import com.gilvano.comprasapi.service.CashbackCalculator

class CashbackCalculatorImpl(private val percentage: Double) : CashbackCalculator {
    override fun calculate(value: Double) = CashbackPercentage(percentage, value * percentage)
}