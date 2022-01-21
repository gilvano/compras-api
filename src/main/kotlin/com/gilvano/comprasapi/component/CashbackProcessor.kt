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
        val associatedPurchases = getAssociatedPurchases(purchase)
        val updatedPurchases = updateCashback(associatedPurchases)
        purchaseRepository.saveAll(updatedPurchases)
        logger.info("Associated purchases for purchase ${purchase.id} processed")
    }

    private fun getAssociatedPurchases(purchase: PurchaseModel): List<PurchaseModel> {
        logger.info("Processing associated purchases for purchase ${purchase.id}")
        return purchaseRepository.findByCpfAndDateBetween(
            purchase.cpf!!,
            purchase.date.with(TemporalAdjusters.firstDayOfMonth()),
            purchase.date.with(TemporalAdjusters.lastDayOfMonth())
        )
    }

    private fun updateCashback(associatedPurchases: List<PurchaseModel>): List<PurchaseModel> {
        logger.info("Updating cashback for associated purchases ${associatedPurchases.map { it.id }}")
        val totalAssociatedPurchases = associatedPurchases.sumOf { it.value }
        val cashbackCalculator = cashbackCalculatorFactory.create(totalAssociatedPurchases)
        return associatedPurchases.map {
            val (percent, cashback ) = cashbackCalculator.calculate(it.value)
            it.cashbackPercentage = percent * 100
            it.cashback = cashback
            it
        }
    }

}