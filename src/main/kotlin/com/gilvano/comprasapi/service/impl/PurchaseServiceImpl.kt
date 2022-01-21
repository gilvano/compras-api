package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.exception.DuclicateResourceException
import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.repository.PurchaseRepository
import com.gilvano.comprasapi.repository.ResellerRepository
import com.gilvano.comprasapi.service.PurchaseService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val resellerRepository: ResellerRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : PurchaseService {

    override fun create(purchase: PurchaseModel) {
        validatePurchase(purchase)
        val purchaseWithCpf = purchase.copy(
            cpf = getCpfFromLogedUser()
        )
        purchaseRepository.save(purchaseWithCpf)
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseWithCpf))
    }

    override fun updatePurchaseStatus(purchase: PurchaseModel) {
        purchaseRepository.save(purchase)
    }

    override fun findAll(pageable: Pageable): Page<PurchaseModel> {
        return purchaseRepository.findAll(pageable)
    }

    private fun validatePurchase(purchase: PurchaseModel) {
        if (purchaseRepository.existsById(purchase.id)) {
            throw DuclicateResourceException(Errors.CP101.message, Errors.CP101.code)
        }
    }

    fun getCpfFromLogedUser(): String {
        val id = SecurityContextHolder.getContext().authentication.principal as String
        val reseller = resellerRepository.findById(id).orElseThrow {
            throw Exception("User not found")
        }
        return reseller.cpf
    }

}