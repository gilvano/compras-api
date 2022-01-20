package com.gilvano.comprasapi.service

import com.gilvano.comprasapi.model.CashbackPercentage

interface CashbackCalculator {
    fun calculate(value: Double): CashbackPercentage
}
