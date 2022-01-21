package com.gilvano.comprasapi.component

import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.repository.PurchaseRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.temporal.TemporalAdjusters

@Component
class CashbackProcessor(
    private val purchaseRepository: PurchaseRepository,
    private val cashbackCalculatorFactory: CashbackCalculatorFactory
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun processAssociatedPurchases(purchase: PurchaseModel) {
        logger.info("Processing associated purchases for purchase ${purchase.id}")
        val associatedPurchases = purchaseRepository.findByCpfAndDateBetween(
            purchase.cpf!!,
            purchase.date.with(TemporalAdjusters.firstDayOfMonth()),
            purchase.date.with(TemporalAdjusters.lastDayOfMonth())
        )

        val totalAssociatedPurchases = associatedPurchases.sumOf { it.value }
        val cashbackCalculator = cashbackCalculatorFactory.create(totalAssociatedPurchases)
        logger.info("Calculating cashback for associated purchases for reseller cpf ${purchase.cpf}")
        val updatedPurchases = associatedPurchases.map {
            val cashback = cashbackCalculator.calculate(it.value)
            it.cashbackPercentage = cashback.percent * 100
            it.cashback = cashback.value
            it
        }
        purchaseRepository.saveAll(updatedPurchases)
        logger.info("Associated purchases for purchase ${purchase.id} processed")
    }
}