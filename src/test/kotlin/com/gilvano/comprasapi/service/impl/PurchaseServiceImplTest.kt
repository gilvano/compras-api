package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.exception.DuclicateResourceException
import com.gilvano.comprasapi.helper.buildPurchase
import com.gilvano.comprasapi.helper.buildReseller
import com.gilvano.comprasapi.repository.PurchaseRepository
import com.gilvano.comprasapi.repository.ResellerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher
import java.util.Optional

@ExtendWith(MockKExtension::class)
class PurchaseServiceImplTest{
    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var resellerRepository: ResellerRepository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMockKs
    @SpyK
    private lateinit var purchaseService: PurchaseServiceImpl

    private val purchaseEventSlot = slot<PurchaseEvent>()

    @Test
    fun `should create purchase and publish event`() {
        val fakeCpf = "12345678901"
        val purchase = buildPurchase(cpf = fakeCpf)
        val reseller = buildReseller(cpf = fakeCpf)

        every { purchaseRepository.save(purchase) } returns purchase
        every { applicationEventPublisher.publishEvent(any()) } just runs
        every { purchaseRepository.existsById(any()) } returns false
        every { resellerRepository.findById(any()) } returns Optional.of(reseller)
        every { purchaseService.getCpfFromLogedUser() } returns fakeCpf

        purchaseService.create(purchase)

        verify(exactly = 1) { purchaseRepository.save(purchase) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(capture(purchaseEventSlot)) }

        assertEquals(purchase, purchaseEventSlot.captured.purchaseModel)
    }

    @Test
    fun `should throw exception when try to save a duplicate purchase`(){
        val purchase = buildPurchase()
        every { purchaseRepository.existsById(any()) } returns true

        val error = assertThrows<DuclicateResourceException> {
            purchaseService.create(purchase)
        }

        Assertions.assertEquals(Errors.CP101.message, error.message)
        Assertions.assertEquals(Errors.CP101.code, error.errorCode)
        verify(exactly = 1) { purchaseRepository.existsById(any()) }
    }
}