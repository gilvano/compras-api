package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.exception.DuclicateResourceException
import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.repository.PurchaseRepository
import com.gilvano.comprasapi.repository.ResellerRepository
import com.gilvano.comprasapi.service.PurchaseService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val resellerRepository: ResellerRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : PurchaseService {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun create(purchase: PurchaseModel) {
        validatePurchase(purchase)
        val purchaseWithCpf = purchase.copy(
            cpf = getCpfFromLoggedUser()
        )
        logger.info("Creating purchase: $purchaseWithCpf")
        purchaseRepository.save(purchaseWithCpf)
        logger.info("Purchase created: $purchaseWithCpf")
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseWithCpf))
    }

    override fun updatePurchaseStatus(purchase: PurchaseModel) {
        purchaseRepository.save(purchase)
    }

    override fun findAll(pageable: Pageable): Page<PurchaseModel> {
        return purchaseRepository.findAllByCpf(
            getCpfFromLoggedUser(),
            pageable)
    }

    private fun validatePurchase(purchase: PurchaseModel) {
        logger.info("Validating purchase: $purchase")
        if (purchaseRepository.existsById(purchase.id)) {
            logger.error("Purchase already exists: $purchase")
            throw DuclicateResourceException(Errors.CP101.message, Errors.CP101.code)
        }
    }

    fun getCpfFromLoggedUser(): String {
        logger.info("Getting cpf from logged user")
        val id = SecurityContextHolder.getContext().authentication.principal as String
        val reseller = resellerRepository.findById(id).orElseThrow {
            logger.error("Reseller not found: $id")
            throw Exception("Reseller not found")
        }
        return reseller.cpf
    }

}