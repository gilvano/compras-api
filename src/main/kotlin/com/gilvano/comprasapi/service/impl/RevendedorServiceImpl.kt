package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.model.RevendedorModel
import com.gilvano.comprasapi.repository.RevendedorRepository
import com.gilvano.comprasapi.service.RevendedorService
import org.springframework.stereotype.Service

@Service
class RevendedorServiceImpl(
    val revendedorRepository: RevendedorRepository
) : RevendedorService {
    override fun create(revendedor: RevendedorModel) {
        revendedorRepository.save(revendedor)
    }
}