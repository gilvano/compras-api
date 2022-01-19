package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.enums.Profile
import com.gilvano.comprasapi.exception.BadRequestException
import com.gilvano.comprasapi.model.RevendedorModel
import com.gilvano.comprasapi.repository.RevendedorRepository
import com.gilvano.comprasapi.service.RevendedorService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class RevendedorServiceImpl(
    private val revendedorRepository: RevendedorRepository,
    private val bCrypt: BCryptPasswordEncoder
) : RevendedorService {
    override fun create(revendedor: RevendedorModel) {
        validarRevendedor(revendedor)
        val revendedorCopy = revendedor.copy(
            roles = Profile.REVENDEDOR,
            senha = bCrypt.encode(revendedor.senha)
        )
        revendedorRepository.save(revendedorCopy)
    }

    private fun validarRevendedor(revendedor: RevendedorModel) {
        if (revendedorRepository.existsByCpf(revendedor.cpf)) {
            throw BadRequestException(Errors.CP001.message, Errors.CP001.code)
        }
        if (revendedorRepository.existsByEmail(revendedor.email)) {
            throw BadRequestException(Errors.CP002.message, Errors.CP002.code)
        }
    }
}