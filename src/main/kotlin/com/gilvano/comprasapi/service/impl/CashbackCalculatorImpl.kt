package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.service.CashbackCalculator

class CashbackCalculatorImpl(private val percentage: Double) : CashbackCalculator {
    override fun calculate(value: Double) = Pair(percentage, value * percentage)
}