package com.gilvano.comprasapi.service

interface CashbackCalculator {
    fun calculate(value: Double): Pair<Double, Double>
}
