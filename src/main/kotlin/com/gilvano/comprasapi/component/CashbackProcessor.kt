package com.gilvano.comprasapi.component

import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.repository.PurchaseRepository
import org.springframework.stereotype.Component
import java.time.temporal.TemporalAdjusters

@Component
class CashbackProcessor(
    private val purchaseRepository: PurchaseRepository,
    private val cashbackCalculatorFactory: CashbackCalculatorFactory
) {
    fun processAssociatedPurchases(purchase: PurchaseModel) {
        val associatedPurchases = purchaseRepository.findByCpfAndDateBetween(
            purchase.cpf!!,
            purchase.date.with(TemporalAdjusters.firstDayOfMonth()),
            purchase.date.with(TemporalAdjusters.lastDayOfMonth())
        )

        val totalAssociatedPurchases = associatedPurchases.sumOf { it.value }
        val cashbackCalculator = cashbackCalculatorFactory.create(totalAssociatedPurchases)

        val updatedPurchases = associatedPurchases.map {
            val cashback = cashbackCalculator.calculate(it.value)
            it.cashbackPercentage = cashback.percent * 100
            it.cashback = cashback.value
            it
        }
        purchaseRepository.saveAll(updatedPurchases)
    }
}