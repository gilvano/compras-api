package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.model.CompraModel
import com.gilvano.comprasapi.repository.CompraRepository
import com.gilvano.comprasapi.service.CompraService
import org.springframework.stereotype.Service

@Service
class CompraServiceImpl(
    private val compraRepository: CompraRepository
) : CompraService {
    override fun create(compra: CompraModel) {
        compraRepository.save(compra)
    }
}