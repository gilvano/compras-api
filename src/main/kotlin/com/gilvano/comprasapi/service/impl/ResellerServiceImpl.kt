package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.enums.Profile
import com.gilvano.comprasapi.exception.BadRequestException
import com.gilvano.comprasapi.model.ResellerModel
import com.gilvano.comprasapi.repository.ResellerRepository
import com.gilvano.comprasapi.service.ResellerService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class ResellerServiceImpl(
    private val resellerRepository: ResellerRepository,
    private val bCrypt: BCryptPasswordEncoder
) : ResellerService {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun create(reseller: ResellerModel) {
        validateReseller(reseller)
        val resellerCopy = reseller.copy(
            profile = Profile.RESELLER,
            password = bCrypt.encode(reseller.password)
        )
        logger.info("Creating reseller: $resellerCopy")
        resellerRepository.save(resellerCopy)
    }

    private fun validateReseller(reseller: ResellerModel) {
        logger.info("Validating reseller: $reseller")
        if (resellerRepository.existsByCpf(reseller.cpf)) {
            logger.error("CPF already exists: ${reseller.cpf}")
            throw BadRequestException(Errors.CP001.message, Errors.CP001.code)
        }
        if (resellerRepository.existsByEmail(reseller.email)) {
            logger.error("Email already exists: ${reseller.email}")
            throw BadRequestException(Errors.CP002.message, Errors.CP002.code)
        }
    }
}