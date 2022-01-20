package com.gilvano.comprasapi.events.listener

import com.gilvano.comprasapi.enums.PurchaseStatus
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.helper.buildPurchase
import com.gilvano.comprasapi.service.PurchaseService
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

    @InjectMockKs
    private lateinit var updatePurchaseListener: UpdatePurchaseListener

    @Test
    fun `should update purchase status`(){
        val CPF_WITH_AUTO_APPROVAL = "15350946056"
        val purchase = buildPurchase(cpf = CPF_WITH_AUTO_APPROVAL)
        val purchaseExpected = purchase.copy(status = PurchaseStatus.APPROVED)

        every { purchaseService.updatePurchaseStatus(purchaseExpected) } just runs

        updatePurchaseListener.listen(PurchaseEvent(this, purchase))

        verify(exactly = 1) { purchaseService.updatePurchaseStatus(purchaseExpected) }
    }

    @Test
    fun `should not update purchase status`(){
        val CPF_WITHOUT_AUTO_APPROVAL = "22129529020"
        val purchase = buildPurchase(cpf = CPF_WITHOUT_AUTO_APPROVAL)
        val purchaseExpected = purchase.copy(status = PurchaseStatus.APPROVED)

        every { purchaseService.updatePurchaseStatus(purchaseExpected) } just runs

        updatePurchaseListener.listen(PurchaseEvent(this, purchase))

        verify(exactly = 0) { purchaseService.updatePurchaseStatus(purchaseExpected) }
    }
}