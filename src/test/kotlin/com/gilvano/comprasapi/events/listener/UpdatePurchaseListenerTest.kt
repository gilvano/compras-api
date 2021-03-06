package com.gilvano.comprasapi.events.listener

import com.gilvano.comprasapi.component.CashbackProcessor
import com.gilvano.comprasapi.enums.PurchaseStatus
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.helper.buildPurchase
import com.gilvano.comprasapi.service.PurchaseService
import com.gilvano.comprasapi.utils.CPF_WITH_AUTO_APPROVAL
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class UpdatePurchaseListenerTest{
    @MockK
    private lateinit var purchaseService: PurchaseService

    @MockK
    private lateinit var cashbackProcessor: CashbackProcessor

    @InjectMockKs
    private lateinit var updatePurchaseListener: UpdatePurchaseListener

    @Test
    fun `should update purchase status`(){
        val purchase = buildPurchase(cpf = CPF_WITH_AUTO_APPROVAL)
        val purchaseExpected = purchase.copy(status = PurchaseStatus.APPROVED)

        every { purchaseService.updatePurchaseStatus(purchaseExpected) } just runs
        every { cashbackProcessor.processAssociatedPurchases(purchaseExpected) } just runs

        updatePurchaseListener.listen(PurchaseEvent(this, purchase))

        verify(exactly = 1) { purchaseService.updatePurchaseStatus(purchaseExpected) }
    }

    @Test
    fun `should not update purchase status`(){
        val fakeCpf = "22129529020"
        val purchase = buildPurchase(cpf = fakeCpf)
        val purchaseExpected = purchase.copy(status = PurchaseStatus.IN_VALIDATION)

        every { purchaseService.updatePurchaseStatus(purchaseExpected) } just runs
        every { cashbackProcessor.processAssociatedPurchases(purchaseExpected) } just runs

        updatePurchaseListener.listen(PurchaseEvent(this, purchase))

        verify(exactly = 0) { purchaseService.updatePurchaseStatus(purchaseExpected) }
    }
}