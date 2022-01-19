package com.gilvano.comprasapi.repository

import com.gilvano.comprasapi.model.ResellerModel
import org.springframework.data.repository.CrudRepository

interface ResellerRepository: CrudRepository<ResellerModel, String> {
    fun existsByCpf(cpf: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): ResellerModel?
}