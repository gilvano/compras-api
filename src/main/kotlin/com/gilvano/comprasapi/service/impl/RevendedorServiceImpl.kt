package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.enums.Errors
import com.gilvano.comprasapi.exception.BadRequestException
import com.gilvano.comprasapi.model.RevendedorModel
import com.gilvano.comprasapi.repository.RevendedorRepository
import com.gilvano.comprasapi.service.RevendedorService
import org.springframework.stereotype.Service

@Service
class RevendedorServiceImpl(
    val revendedorRepository: RevendedorRepository
) : RevendedorService {
    override fun create(revendedor: RevendedorModel) {
        if (revendedorRepository.existsByCpf(revendedor.cpf)) {
            throw BadRequestException(Errors.CP001.message, Errors.CP001.code)
        }
        revendedorRepository.save(revendedor)
    }
}