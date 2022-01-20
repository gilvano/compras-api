package com.gilvano.comprasapi.component

import com.gilvano.comprasapi.helper.buildPurchase
import com.gilvano.comprasapi.repository.PurchaseRepository
import com.gilvano.comprasapi.service.impl.CashbackCalculatorImpl
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.temporal.TemporalAdjusters

@ExtendWith(MockKExtension::class)
class CashbackProcessorTest{
    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var cashbackCalculatorFactory: CashbackCalculatorFactory

    @InjectMockKs
    private lateinit var cashbackProcessor: CashbackProcessor

    @Test
    fun `should calculate and update cashback of purchases with ten percent`(){
        val purchase = buildPurchase(id = 3, value = 100.0)
        val purchases = listOf(
            buildPurchase(id = 1, value = 500.0),
            buildPurchase(id = 2, value = 400.0),
            purchase)
        val purchasesExpected = listOf(
            buildPurchase(id = 1, value = 500.0, cashback = 50.0, cashbackPercentage = 10.0),
            buildPurchase(id = 2, value = 400.0, cashback = 40.0, cashbackPercentage = 10.0),
            buildPurchase(id = 3, value = 100.0, cashback = 10.0, cashbackPercentage = 10.0),
        )

        every { purchaseRepository.findByCpfAndDateBetween(
            purchase.cpf,
            purchase.date.with(TemporalAdjusters.firstDayOfMonth()),
            purchase.date.with(TemporalAdjusters.lastDayOfMonth())
        )
        } returns purchases
        every { purchaseRepository.saveAll(purchasesExpected) }  returns purchasesExpected
        every { cashbackCalculatorFactory.create(1000.0) } returns CashbackCalculatorImpl(0.10)

        cashbackProcessor.processAssociatedPurchases(purchase)

        verify(exactly = 1) { purchaseRepository.saveAll(purchasesExpected) }
    }

    @Test
    fun `should calculate and update cashback of purchases with fifteen percent`(){
        val purchase = buildPurchase(id = 3, value = 100.0)
        val purchases = listOf(
            buildPurchase(id = 1, value = 500.0),
            buildPurchase(id = 2, value = 600.0),
            purchase)
        val purchasesExpected = listOf(
            buildPurchase(id = 1, value = 500.0, cashback = 75.0, cashbackPercentage = 15.0),
            buildPurchase(id = 2, value = 600.0, cashback = 90.0, cashbackPercentage = 15.0),
            buildPurchase(id = 3, value = 100.0, cashback = 15.0, cashbackPercentage = 15.0),
        )

        every { purchaseRepository.findByCpfAndDateBetween(
                purchase.cpf,
                purchase.date.with(TemporalAdjusters.firstDayOfMonth()),
                purchase.date.with(TemporalAdjusters.lastDayOfMonth())
            )
        } returns purchases
        every { purchaseRepository.saveAll(purchasesExpected) }  returns purchasesExpected
        every { cashbackCalculatorFactory.create(1200.0) } returns CashbackCalculatorImpl(0.15)

        cashbackProcessor.processAssociatedPurchases(purchase)

        verify(exactly = 1) { purchaseRepository.saveAll(purchasesExpected) }
    }

    @Test
    fun `should calculate and update cashback of purchases with twenty percent`(){
        val purchase = buildPurchase(id = 3, value = 200.0)
        val purchases = listOf(
            buildPurchase(id = 1, value = 1000.0),
            buildPurchase(id = 2, value = 800.0),
            purchase)
        val purchasesExpected = listOf(
            buildPurchase(id = 1, value = 1000.0, cashback = 200.0, cashbackPercentage = 20.0),
            buildPurchase(id = 2, value = 800.0, cashback = 160.0, cashbackPercentage = 20.0),
            buildPurchase(id = 3, value = 200.0, cashback = 40.0, cashbackPercentage = 20.0),
        )

        every { purchaseRepository.findByCpfAndDateBetween(
            purchase.cpf,
            purchase.date.with(TemporalAdjusters.firstDayOfMonth()),
            purchase.date.with(TemporalAdjusters.lastDayOfMonth())
        )
        } returns purchases
        every { purchaseRepository.saveAll(purchasesExpected) }  returns purchasesExpected
        every { cashbackCalculatorFactory.create(2000.0) } returns CashbackCalculatorImpl(0.20)

        cashbackProcessor.processAssociatedPurchases(purchase)

        verify(exactly = 1) { purchaseRepository.saveAll(purchasesExpected) }
    }
}