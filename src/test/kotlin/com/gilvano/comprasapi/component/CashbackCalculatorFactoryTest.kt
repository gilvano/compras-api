package com.gilvano.comprasapi.component

import com.gilvano.comprasapi.helper.buildCriteria
import com.gilvano.comprasapi.service.impl.ResellerServiceImpl
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CashbackCalculatorFactoryTest{

    @MockK
    private lateinit var cashbackCriteriaProperties: CashbackCriteriaProperties

    @InjectMockKs
    private lateinit var cashbackCalculatorFactory: CashbackCalculatorFactory

    private lateinit var criteriaFake: MutableList<Criteria>

    @BeforeAll
    fun setup(){
        criteriaFake = getCriteriaFake()
    }

    @Test
    fun `should return ten percent cashback`(){
        val purchaseValue = 1000.0
        val expectedPercent = 0.10
        val expectedCashback = purchaseValue * expectedPercent
        every { cashbackCriteriaProperties.criteria } returns criteriaFake

        val cashbackCalculator = cashbackCalculatorFactory.create(purchaseValue)
        val (percent, cashback ) = cashbackCalculator.calculate(purchaseValue)
        assertEquals(expectedPercent, percent)
        assertEquals(expectedCashback, cashback)
    }

    @Test
    fun `should return fifteen percent cashback`(){
        val purchaseValue = 1500.0
        val expectedPercent = 0.15
        val expectedCashback = purchaseValue * expectedPercent
        every { cashbackCriteriaProperties.criteria } returns criteriaFake

        val cashbackCalculator = cashbackCalculatorFactory.create(purchaseValue)
        val (percent, cashback ) = cashbackCalculator.calculate(purchaseValue)
        assertEquals(expectedPercent, percent)
        assertEquals(expectedCashback, cashback)
    }

    @Test
    fun `should return twenty percent cashback`(){
        val purchaseValue = 2000.0
        val expectedPercent = 0.20
        val expectedCashback = purchaseValue * expectedPercent
        every { cashbackCriteriaProperties.criteria } returns criteriaFake

        val cashbackCalculator = cashbackCalculatorFactory.create(purchaseValue)
        val (percent, cashback ) = cashbackCalculator.calculate(purchaseValue)
        assertEquals(expectedPercent, percent)
        assertEquals(expectedCashback, cashback)
    }

    private fun getCriteriaFake(): MutableList<Criteria> {
        return mutableListOf(
            buildCriteria(valueAbove = 0.0, cashbackPercent = 0.10),
            buildCriteria(valueAbove = 1000.0, cashbackPercent = 0.15),
            buildCriteria(valueAbove = 1500.0, cashbackPercent = 0.20)
        )
    }

}