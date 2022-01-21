package com.gilvano.comprasapi.component

import com.gilvano.comprasapi.service.CashbackCalculator
import com.gilvano.comprasapi.service.impl.CashbackCalculatorImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CashbackCalculatorFactory(
    private val cashbackCriteria: CashbackCriteriaProperties
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun create(value: Double): CashbackCalculator {
        var cashbackPercentage: Double = 0.0
        cashbackCriteria.criteria
            .sortedByDescending{ it.valueAbove }
            .first { value > it.valueAbove!! }
            .let{ cashbackPercentage = it.cashbackPercent!! }

        logger.info("Cashback percentage: $cashbackPercentage")
        return CashbackCalculatorImpl(cashbackPercentage)
    }
}
