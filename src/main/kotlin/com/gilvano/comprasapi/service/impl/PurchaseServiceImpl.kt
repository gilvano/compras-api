package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.events.PurchaseEvent
import com.gilvano.comprasapi.exception.DuclicateResourceException
import com.gilvano.comprasapi.gateway.ExternalApiClient
import com.gilvano.comprasapi.model.PurchaseModel
import com.gilvano.comprasapi.repository.PurchaseRepository
import com.gilvano.comprasapi.service.PurchaseService
import com.gilvano.comprasapi.utils.TOKEN
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val resellerService: ResellerServiceImpl,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val externalApiClient: ExternalApiClient
) : PurchaseService {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Value("\${third-party.token}")
    private lateinit var token: String

    override fun create(purchase: PurchaseModel) {
        validatePurchase(purchase)
        val purchaseWithCpf = purchase.copy(
            cpf = resellerService.getCpfFromLoggedReseller()
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
            resellerService.getCpfFromLoggedReseller(),
            pageable)
    }

    private fun validatePurchase(purchase: PurchaseModel) {
        logger.info("Validating purchase: $purchase")
        if (purchaseRepository.existsById(purchase.id)) {
            logger.error("Purchase already exists: $purchase")
            throw DuclicateResourceException(Errors.CP101.message, Errors.CP101.code)
        }
    }

    override fun getAccumulatedCashback(): Double {
        logger.info("Getting accumulated cashback")
        val tokenHeader = mapOf(TOKEN to token)
        val caskback =  externalApiClient
            .getAccumulatedCashbackByCpf(tokenHeader, resellerService.getCpfFromLoggedReseller())
        return caskback.body.credit
    }
}